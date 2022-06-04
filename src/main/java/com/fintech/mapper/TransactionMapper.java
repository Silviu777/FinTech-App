package com.fintech.mapper;

import com.fintech.dto.TransactionDto;
import com.fintech.model.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionMapper {

    public static TransactionDto mapToDto(Transaction transaction) {

        if (transaction == null) {
            return null;
        }

        return TransactionDto.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency()) // delete currency ?
                .description(transaction.getDescription())
                .transactionDate(transaction.getTransactionDate())
                .status(transaction.getStatus())
                .build();
    }

    public static Transaction maptoEntity(TransactionDto transactionDTO) {

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

    public static List<TransactionDto> mapListToDto(List<Transaction> transactions) {
        return transactions.stream().map(TransactionMapper::mapToDto).collect(Collectors.toList());
    }

}
