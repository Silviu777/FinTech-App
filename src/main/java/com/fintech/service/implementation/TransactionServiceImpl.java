package com.fintech.service.implementation;

import com.fintech.config.JwtTokenUtil;
import com.fintech.dto.TransactionRequestDto;
import com.fintech.model.Account;
import com.fintech.model.Transaction;
import com.fintech.model.enums.Currency;
import com.fintech.model.enums.TransactionStatus;
import com.fintech.repository.AccountRepository;
import com.fintech.repository.TransactionRepository;
import com.fintech.service.AccountService;
import com.fintech.service.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final Logger logger = LogManager.getLogger(getClass());


    @Override
    public boolean findTransactionById(Long id) {
        return transactionRepository.findById(id).isPresent();
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public void transfer(TransactionRequestDto transactionRequest) {

        String senderIban = transactionRequest.getSenderIban();
        String receiverIban = transactionRequest.getReceiverIban();

        Account sender = accountService.getAccountByIBAN(senderIban);
        Account receiver = accountService.getAccountByIBAN(receiverIban);
        BigDecimal amount = transactionRequest.getAmount();
        Currency currency = transactionRequest.getCurrency();
        String description = transactionRequest.getDescription();

        if (sender.equals(receiver)) {
            throw new RuntimeException("You cannot send funds to your own account! Please select another account.");
        }

        if (sender.getBalance().compareTo(amount) < 0 || amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new RuntimeException("You do not have enough funds in your account to complete the transfer!");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));
        accountService.updateAccount(sender);
        accountService.updateAccount(receiver);

        Transaction transaction = new Transaction();
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setAccount(sender);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setDescription(description);
        transaction.setTransactionDate(new Date());
        saveTransaction(transaction);

        verifyTransfer(transaction);

    }

    @Override
    public void verifyTransfer(Transaction transaction) {
        if (findTransactionById(transaction.getId()) && transaction.getDescription() != null) {
            transaction.setStatus(TransactionStatus.VERIFIED);
            saveTransaction(transaction);
        }

        else if (transaction.getDescription() == null) {
            transaction.setStatus(TransactionStatus.PENDING);
            saveTransaction(transaction);
        }

        else {
            transaction.setStatus(TransactionStatus.REJECTED);
        }
    }

    @Override
    public List<Transaction> getTransactionsHistory(String token) {
        String accountNo = jwtTokenUtil.getUsernameFromToken(token);
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(transactionRepository.findAllByAccount_AccountNo(accountNo));
        transactions.sort(Collections.reverseOrder());
        return transactions;
    }

    @Override
    public List<Transaction> viewTransactionsByStatus(TransactionStatus status) {
        switch (status) {
            case PENDING:
                return transactionRepository.findAllByStatus(TransactionStatus.PENDING);

            case REJECTED:
                return transactionRepository.findAllByStatus(TransactionStatus.REJECTED);

            case VERIFIED:
                return transactionRepository.findAllByStatus(TransactionStatus.VERIFIED);

            default:
                throw new RuntimeException("Could not find any " + status + " transactions!");
        }
    }

    @Override
    public List<Transaction> viewTransactionsByCurrency(Currency currency) {
        switch (currency) {
            case RON:
                return transactionRepository.findAllByCurrency(Currency.RON);

            case EUR:
                return transactionRepository.findAllByCurrency(Currency.EUR);

            case USD:
                return transactionRepository.findAllByCurrency(Currency.USD);

            case GBP:
                return transactionRepository.findAllByCurrency(Currency.GBP);

            default:
                throw new RuntimeException("Could not find any transactions in " + currency + " !");
        }
    }
}
