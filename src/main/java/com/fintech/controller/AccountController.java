package com.fintech.controller;

import com.fintech.dto.*;
import com.fintech.mapper.AccountMapper;
import com.fintech.mapper.TransactionMapper;
import com.fintech.model.Account;
import com.fintech.model.Transaction;
import com.fintech.service.AccountService;
import com.fintech.service.TransactionService;
import com.fintech.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    private final Logger logger = LogManager.getLogger(getClass());


    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDTO>> getAllAcounts() {
        return ResponseEntity.ok(AccountMapper.mapListToDto(accountService.getAllAccounts()));
    }

    @GetMapping("/account")
    public ResponseEntity<Account> getAccountDetails(HttpServletRequest request, HttpServletResponse response) {
        try {
            return ResponseEntity.ok(accountService.getAccountFromToken(request.getHeader("token")));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/transaction")
    public ResponseEntity<List<Transaction>> getTransactionHistory(HttpServletRequest request, HttpServletResponse response) {
        try {
            return ResponseEntity.ok(transactionService.getTransactionsHistory(request.getHeader("token")));
        } catch (Exception e) {
            logger.error("error produced getting transaction history : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<AccountDTO> displayAccount(@PathVariable Long id) {
        return ResponseEntity.ok(AccountMapper.mapToDto(accountService.getAccount(id)));
    }

    @GetMapping("/account/{id}/transactions")
    public ResponseEntity<List<TransactionDto>> listAccountTransactions(@PathVariable Long id) {
        return ResponseEntity.ok(TransactionMapper.mapListToDto(accountService.viewAccountTransactions(id)));
    }

    @PostMapping("/user/{id}/account")
    public ResponseEntity<NewAccountDtoOutput> createAccount(@PathVariable Long id, @RequestBody NewAccountDtoInput newAccount) {
        return ResponseEntity.ok(accountService.openAccount(newAccount, userService.findById(id).getUsername()));
    }

    @PostMapping("/account/{id}/deposit")
    public ResponseEntity depositAmount(@PathVariable Long id, @RequestBody DepositAmountDto depositAmount) {
        accountService.deposit(id, depositAmount.getAmount());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}