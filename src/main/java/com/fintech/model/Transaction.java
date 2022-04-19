package com.fintech.model;

import com.fintech.model.enums.Currency;
import com.fintech.model.enums.TransactionStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER) // review!!
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;


}
