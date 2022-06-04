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
public class NewAccountDtoInput {

    private AccountType accountType;

    private Currency currency;

    private BigDecimal balance;

}
