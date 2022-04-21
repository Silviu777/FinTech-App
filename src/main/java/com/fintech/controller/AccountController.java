package com.fintech.controller;

import com.fintech.dto.AccountDTO;
import com.fintech.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<AccountDTO> getAllAcounts() {
        return accountService.getAllAccounts().stream().map(account -> modelMapper.map(account, AccountDTO.class)).collect(Collectors.toList());
    }
}
