package com.fintech.controller;

import com.fintech.utils.OperationsCodes;
import com.fintech.dto.Response;
import com.fintech.dto.UserDto;
import com.fintech.exception.BadRequestException;
import com.fintech.mapper.UserMapper;
import com.fintech.model.Account;
import com.fintech.model.User;
import com.fintech.service.AccountService;
import com.fintech.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    private final Logger logger = LogManager.getLogger(getClass());


    @GetMapping("/user")
    public ResponseEntity<User> getUserFromToken(HttpServletRequest request) {
        try {
            return ResponseEntity.ok(userService.getUserFromToken(request.getHeader("token")));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> displayUserById(@PathVariable Long id) {
        return ResponseEntity.ok(UserMapper.mapToDto(userService.findById(id)));
    }

    @PostMapping("/user")
    public ResponseEntity<Response> createUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(new Response(OperationsCodes.SUCCESS, userService.createUser(user)));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(OperationsCodes.ERROR, e.getMessage()));
        } catch (Exception e) {
            logger.error("Register error : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(OperationsCodes.ERROR, e.getMessage()));
        }
    }

    @PutMapping("/user")
    public ResponseEntity<Response> updateUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(new Response(OperationsCodes.SUCCESS, userService.updateUser(user)));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(OperationsCodes.ERROR, e.getMessage()));
        } catch (Exception e) {
            logger.info("User data update error : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(OperationsCodes.ERROR, e.getMessage()));
        }
    }

    @GetMapping("/{id}/account")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountByUserId(id));
    }
}
