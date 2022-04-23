package com.fintech.service.implementation;

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
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;


    @Override
    public boolean findTransactionById(Long id) {
        return transactionRepository.findById(id).isPresent();
    }

    @Override
    public void transfer(Account sender, Account receiver, BigDecimal amount) {
        Transaction transaction = new Transaction();

        if (sender.equals(receiver)) {
            throw new RuntimeException("You cannot send funds to your own account!");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("You do not have enough funds in your account to complete the transfer!");
        }

        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new RuntimeException("Please increase your amount to complete the transfer!");
        }

        transaction.setAmount(amount);
        transaction.setDescription("Transfer from " + sender.getOwner().getUserName() + " to " + receiver.getOwner().getUserName()); // display account id/iban or keep the user?
        transaction.setTransactionDate(new Date());
        transaction.setStatus(TransactionStatus.PENDING);
        saveTransaction(transaction);

        sender.setBalance(sender.getBalance().subtract(amount));
        accountService.updateAccount(sender);
        receiver.setBalance(receiver.getBalance().add(amount));
        accountService.updateAccount(receiver);

        verifyTransfer(transaction); // TO BE REVIEWED!
    }

    @Override
    public void verifyTransfer(Transaction transaction) {
        if (findTransactionById(transaction.getId())) {
            transaction.setStatus(TransactionStatus.VERIFIED);
        }
        else {
            transaction.setStatus(TransactionStatus.REJECTED);
        }
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
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
