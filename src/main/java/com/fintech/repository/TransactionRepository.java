package com.fintech.repository;

import com.fintech.model.Account;
import com.fintech.model.Transaction;
import com.fintech.model.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByAccount(Account account);

    List<Transaction> findAllByCurrency(Currency currency);

    List<Transaction> findAllByStatus(TransactionStatus transactionStatus);

    List<Transaction> findAllByTransactionDateBetween(LocalDate date1, LocalDate date2);

    List<Transaction> findAllByAmountGreaterThan(BigDecimal amount);

    List<Transaction> findAllByAmountLessThan(BigDecimal amount);

    List<Transaction> findAllByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);


}