package com.fintech.mapper;

import com.fintech.dto.AccountDTO;
import com.fintech.model.Account;

import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper {

    public static AccountDTO mapToDto(Account account) {

        if (account == null) {
            return null;
        }

        return AccountDTO.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .accountType(account.getAccountType())
                .currency(account.getCurrency())
                .dateOpened(account.getDateOpened())
                .iban(account.getIban())
                .transactionList(account.getTransactionList())
                .build();
    }
    
    public static Account mapToEntity(AccountDTO accountDTO) {
        
        if (accountDTO == null) {
            return null;
        }

        return Account.builder()
                .id(accountDTO.getId())
                .balance(accountDTO.getBalance())
                .accountType(accountDTO.getAccountType())
                .currency(accountDTO.getCurrency())
                .dateOpened(accountDTO.getDateOpened())
                .iban(accountDTO.getIban())
                .transactionList(accountDTO.getTransactionList())
                .build();
    }

    public static List<AccountDTO> mapListToDto(List<Account> accounts) {
            return accounts.stream().map(AccountMapper::mapToDto).collect(Collectors.toList());
    }
}
