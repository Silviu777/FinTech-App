package com.fintech.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fintech.model.Transaction;
import com.fintech.model.enums.AccountType;
import com.fintech.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Long id;

    private BigDecimal balance;

    private String iban;

    private AccountType accountType;

    private Currency currency;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOpened;

    private List<Transaction> transactionList;

}
