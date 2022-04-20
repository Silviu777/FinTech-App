package com.fintech.service;

import com.fintech.model.Account;

import java.math.BigDecimal;

public interface TransactionService {

    void transfer(Account sender, Account receiver, BigDecimal amount); // only receiver + add amount?

    void verifyTransfer(); // for setting VALIDATED Status -> to be reviewed
}
