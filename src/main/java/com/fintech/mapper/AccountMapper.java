package com.fintech.mapper;

import com.fintech.dto.AccountDto;
import com.fintech.model.Account;

import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper {

    public static AccountDto mapToDto(Account account) {

        if (account == null) {
            return null;
        }

        return AccountDto.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .accountType(account.getAccountType())
                .currency(account.getCurrency())
                .dateOpened(account.getDateOpened())
                .iban(account.getIban())
                .transactionList(account.getTransactionList())
                .build();
    }
    
    public static Account mapToEntity(AccountDto accountDTO) {
        
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

    public static List<AccountDto> mapListToDto(List<Account> accounts) {
            return accounts.stream().map(AccountMapper::mapToDto).collect(Collectors.toList());
    }
}
