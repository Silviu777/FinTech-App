package com.fintech.dto;

import com.fintech.model.enums.Currency;
import com.fintech.model.enums.TransactionStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionDTO {

    private Long id;

    private BigDecimal amount;

    private Currency currency;

    private String description;

    private Date transactionDate;

    private TransactionStatus status;

}
