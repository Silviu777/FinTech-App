package com.fintech.controller;

import com.fintech.dto.*;
import com.fintech.mapper.AccountMapper;
import com.fintech.mapper.TransactionMapper;
import com.fintech.service.AccountService;
import com.fintech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDTO>> getAllAcounts() {
        return ResponseEntity.ok(AccountMapper.mapListToDto(accountService.getAllAccounts()));
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
        return ResponseEntity.ok(accountService.openAccount(newAccount, userService.findById(id).getUserName()));
    }

    @PostMapping("/account/{id}/deposit")
    public ResponseEntity depositAmount(@PathVariable Long id, @RequestBody DepositAmountDto depositAmount) {
        accountService.deposit(id, depositAmount.getAmount());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}