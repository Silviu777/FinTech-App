package com.fintech.dto;

import com.fintech.model.enums.AccountType;
import com.fintech.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewAccountDtoOutput {

    private String accountIban;

    private String username;

    private AccountType accountType;

    private BigDecimal balance;

    private Currency currency;

}
