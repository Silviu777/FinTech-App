package com.fintech.dto;

import com.fintech.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDto {

    private String senderIban;

    private String receiverIban;

    private BigDecimal amount;

    private Currency currency;

    private String description;

}
