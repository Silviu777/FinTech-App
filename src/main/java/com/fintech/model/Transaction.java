package com.fintech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fintech.model.enums.Currency;
import com.fintech.model.enums.TransactionStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;   // keep it or delete it?

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER) // review!!
    @JoinColumn(name = "account_id")
    @JsonIgnore()
    private Account account;

    @Column(name = "transaction_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date transactionDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    public Transaction(BigDecimal amount, Account account, Date date) {

    }
}
