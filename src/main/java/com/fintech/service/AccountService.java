package com.fintech.service;

import com.fintech.model.Account;
import com.fintech.model.Transaction;
import com.fintech.model.User;
import com.fintech.model.enums.AccountType;
import com.fintech.model.enums.Currency;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    Account getAccount(Long id);
    Account openAccount(User user, AccountType accountType, Currency currency);
    void deposit(Long accountId, BigDecimal amount);
    void closeAccount(User user);
    Account updateAccount(Account account);
    List<Transaction> viewAccountTransactions(Long accountId);
}