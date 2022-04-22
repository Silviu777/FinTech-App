package com.fintech.mapper;

import com.fintech.dto.TransactionDTO;
import com.fintech.model.Transaction;

public class TransactionMapper {

    public static TransactionDTO mapToDto(Transaction transaction) {

        if (transaction == null) {
            return null;
        }

        return TransactionDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .description(transaction.getDescription())
                .transactionDate(transaction.getTransactionDate())
                .status(transaction.getStatus())
                .build();
    }

    public static Transaction maptoEntity(TransactionDTO transactionDTO) {

        if (transactionDTO == null) {
            return null;
        }

        return Transaction.builder()
                .id(transactionDTO.getId())
                .amount(transactionDTO.getAmount())
                .currency(transactionDTO.getCurrency())
                .description(transactionDTO.getDescription())
                .transactionDate(transactionDTO.getTransactionDate())
                .status(transactionDTO.getStatus())
                .build();
    }
}
