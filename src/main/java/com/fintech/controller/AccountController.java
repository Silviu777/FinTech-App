package com.fintech.controller;

import com.fintech.dto.*;
import com.fintech.exception.BadRequestException;
import com.fintech.mapper.AccountMapper;
import com.fintech.mapper.TransactionMapper;
import com.fintech.model.Account;
import com.fintech.model.Transaction;
import com.fintech.service.AccountService;
import com.fintech.service.TransactionService;
import com.fintech.utils.OperationsCodes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    private final Logger logger = LogManager.getLogger(getClass());


    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDto>> getAllAcounts() {
        return ResponseEntity.ok(AccountMapper.mapListToDto(accountService.getAllAccounts()));
    }

    @GetMapping("/account")
    public ResponseEntity<Account> getAccountDetails(HttpServletRequest request) {
        try {
            return ResponseEntity.ok(accountService.getAccountFromToken(request.getHeader("token")));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/transaction")
    public ResponseEntity<List<Transaction>> getTransactionHistory(HttpServletRequest request) {
        try {
            return ResponseEntity.ok(transactionService.getTransactionsHistory(request.getHeader("token")));
        } catch (Exception e) {
            logger.error("Error during transaction history fetch : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<Response> transferMoney(@RequestBody TransactionRequestDto transactionDto, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(new Response(OperationsCodes.SUCCESS, transactionService.transfer(transactionDto, request.getHeader("token"))));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(OperationsCodes.ERROR, e.getMessage()));
        } catch (Exception e) {
            logger.error("Error during money transfer operation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(OperationsCodes.ERROR, e.getMessage()));
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<Response> depositMoney(@RequestBody DepositDto deposit, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(new Response(OperationsCodes.SUCCESS, accountService.deposit(deposit, request.getHeader("token"))));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(OperationsCodes.ERROR, e.getMessage()));
        } catch (Exception e) {
            logger.error("Error during money deposit operation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(OperationsCodes.ERROR, e.getMessage()));
        }
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<AccountDto> displayAccount(@PathVariable Long id) {
        return ResponseEntity.ok(AccountMapper.mapToDto(accountService.getAccount(id)));
    }

    @GetMapping("/account/{id}/transactions")
    public ResponseEntity<List<TransactionDto>> listAccountTransactions(@PathVariable Long id) {
        return ResponseEntity.ok(TransactionMapper.mapListToDto(accountService.viewAccountTransactions(id)));
    }
}