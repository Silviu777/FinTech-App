package com.fintech.dto;

import com.fintech.model.Transaction;
import com.fintech.model.enums.AccountType;
import com.fintech.model.enums.Currency;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class AccountDTO {

    private Long id;

    private BigDecimal balance;

    private String iban;

    private AccountType accountType;

    private Currency currency;

    private Date dateOpened;

    private List<Transaction> transactionList;

}
