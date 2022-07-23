package com.fintech.controller;


import com.fintech.dto.Response;
import com.fintech.dto.TransactionRequestDto;
import com.fintech.exception.BadRequestException;
import com.fintech.service.TransactionService;
import com.fintech.utils.OperationsCodes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")

public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    private final Logger logger = LogManager.getLogger(getClass());


    @PostMapping("/moneyTransfer")
    public ResponseEntity<Response> transferMoney(@RequestBody TransactionRequestDto transactionDto, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(new Response(OperationsCodes.SUCCESS, transactionService.transfer(transactionDto, request.getHeader("token"))));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(OperationsCodes.ERROR, e.getMessage()));
        } catch (Exception e) {
            logger.error("error produced during transfer money : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(OperationsCodes.ERROR, e.getMessage()));
        }
    }
}
