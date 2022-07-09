package com.fintech.service;

import com.fintech.dto.NewAccountDtoInput;
import com.fintech.dto.NewAccountDtoOutput;
import com.fintech.model.Account;
import com.fintech.model.Transaction;
import com.fintech.model.User;
import com.fintech.model.enums.AccountType;
import com.fintech.model.enums.Currency;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    List<Account> getAllAccounts();

    Account getAccount(Long id);

    String createAccount(User user);

    Account getAccountByAccountNo(String accountNo);

    Account getAccountByUsername(String username);

    Account getAccountFromToken(String token);

    Account getAccountByUserId(Long userId);

    Account getAccountByIBAN(String iban);

    NewAccountDtoOutput openAccount(NewAccountDtoInput newAccount, String username);

    void deposit(Long accountId, BigDecimal amount);

    void closeAccount(User user);

    void updateAccount(Account account);

    List<Transaction> viewAccountTransactions(Long accountId);

}