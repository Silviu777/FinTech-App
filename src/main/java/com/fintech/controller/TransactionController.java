package com.fintech.controller;


import com.fintech.model.Account;
import com.fintech.service.AccountService;
import com.fintech.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transaction")

public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;
    

    @PostMapping("/add")
    public ResponseEntity<?> performTransaction(String sender, String receiver, BigDecimal amount) {

            Account senderAccount = accountService.getAccountByUsername(sender);
            Account receiverAccount = accountService.getAccountByUsername(receiver);
            transactionService.transfer(senderAccount, receiverAccount, amount);

            return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
