package com.fintech.service;

import com.fintech.dto.TransactionRequestDTO;
import com.fintech.model.Transaction;
import com.fintech.model.enums.Currency;
import com.fintech.model.enums.TransactionStatus;

import java.util.List;

public interface TransactionService {

    boolean findTransactionById(Long id);

    void saveTransaction(Transaction transaction);

    void transfer(TransactionRequestDTO transaction);

    void verifyTransfer(Transaction transaction); // for setting VALIDATED Status -> to be reviewed

    List<Transaction> viewTransactionsByStatus(TransactionStatus status);

    List<Transaction> viewTransactionsByCurrency(Currency currency);

}
