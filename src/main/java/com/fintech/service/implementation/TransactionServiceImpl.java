package com.fintech.service.implementation;

import com.fintech.config.JwtTokenUtil;
import com.fintech.utils.OperationsCodes;
import com.fintech.dto.TransactionRequestDto;
import com.fintech.exception.BadRequestException;
import com.fintech.model.Account;
import com.fintech.model.Transaction;
import com.fintech.model.enums.Currency;
import com.fintech.model.enums.TransactionStatus;
import com.fintech.repository.TransactionRepository;
import com.fintech.service.AccountService;
import com.fintech.service.TransactionService;
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


    @Override
    public boolean findTransactionById(Long id) {
        return transactionRepository.findById(id).isPresent();
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public String transfer(TransactionRequestDto transactionRequest, String senderToken) {
        String senderAccount = jwtTokenUtil.getUsernameFromToken(senderToken);
        String receiverIban = transactionRequest.getReceiverIban();

        Account sender = accountService.getAccountByAccountNo(senderAccount);
        Account receiver = accountService.getAccountByIBAN(receiverIban);
        BigDecimal amount = transactionRequest.getAmount();
        Currency currency = transactionRequest.getCurrency();
        String description = transactionRequest.getDescription();

        if (sender.equals(receiver)) {
            throw new BadRequestException("You cannot send funds to your own account! Please select another account.");
        }

        if (sender.getBalance().compareTo(amount) < 0 || amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new BadRequestException("You do not have enough funds in your account to complete the transfer!");
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
        transaction.setSender(sender.getIban());
        transaction.setReceiver(receiverIban);
        saveTransaction(transaction);

        verifyTransfer(transaction);
        return OperationsCodes.TRANSACTION_DONE;
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
        Account account = accountService.getAccountByAccountNo(accountNo);

        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(transactionRepository.findByReceiver(account.getIban()));
        transactions.addAll(transactionRepository.findBySender(account.getIban()));
        transactions.sort(Collections.reverseOrder());

        return transactions;
    }
}
