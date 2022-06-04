package com.fintech.controller;


import com.fintech.dto.TransactionRequestDto;
import com.fintech.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")

public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/add")
    public ResponseEntity<?> performTransaction(@RequestBody TransactionRequestDto transactionRequestDTO) {
            transactionService.transfer(transactionRequestDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
