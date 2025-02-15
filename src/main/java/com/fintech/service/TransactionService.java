package com.fintech.service;

import com.fintech.dto.TransactionRequestDto;
import com.fintech.model.Transaction;
import com.fintech.model.enums.Currency;
import com.fintech.model.enums.TransactionStatus;

import java.util.List;

public interface TransactionService {

    boolean findTransactionById(Long id);

    void saveTransaction(Transaction transaction);

    String transfer(TransactionRequestDto transaction, String senderToken);

    void verifyTransfer(Transaction transaction);

    List<Transaction> getTransactionsHistory(String token);

}
