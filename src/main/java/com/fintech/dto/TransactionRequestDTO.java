package com.fintech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionRequestDTO {

    private String senderIban;

    private String receiverIban;

    private BigDecimal amount;

    private String description;

}
