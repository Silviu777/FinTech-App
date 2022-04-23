package com.fintech.controller;

import com.fintech.dto.AccountDTO;
import com.fintech.mapper.AccountMapper;
import com.fintech.model.Account;
import com.fintech.service.AccountService;
import com.fintech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/user/{id}/account")
    public ResponseEntity<Account> createAccount(@PathVariable Long id, @RequestBody Account account) {
        return ResponseEntity.ok(accountService.openAccount(userService.findById(id), account.getAccountType(), account.getCurrency()));
    }
}
