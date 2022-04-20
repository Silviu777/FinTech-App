package com.fintech.service.implementation;

import com.fintech.model.Account;
import com.fintech.model.Transaction;
import com.fintech.model.enums.TransactionStatus;
import com.fintech.repository.TransactionRepository;
import com.fintech.service.AccountService;
import com.fintech.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public void transfer(Account sender, Account receiver, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("Transfer from " + sender.getOwner().getUserName() + " to " + receiver.getOwner().getUserName()); // display account id/iban or keep the user?
        transaction.setTransactionDate(new Date());
        transaction.setStatus(TransactionStatus.PENDING);

        transactionRepository.save(transaction);

        sender.setBalance(sender.getBalance().subtract(amount));
        accountService.updateAccount(sender);

        receiver.setBalance(receiver.getBalance().add(amount));
        accountService.updateAccount(receiver);
    }

    @Override
    public void verifyTransfer() { }
}
