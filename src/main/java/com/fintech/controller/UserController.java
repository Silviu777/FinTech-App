package com.fintech.controller;

import com.fintech.dto.UserDTO;
import com.fintech.mapper.UserMapper;
import com.fintech.model.User;
import com.fintech.model.enums.Role;
import com.fintech.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> displayUserById(@PathVariable Long id) {

        return ResponseEntity.ok(UserMapper.mapToDto(userService.findById(id)));
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestParam(defaultValue="CLIENT") Role role, @RequestBody User newUser) {

        if (userService.checkEmailAddress(newUser.getEmailAddress())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An user is already registered with this email address! Please log in with the existing credentials or provide another email address.");
        }
        // TO PROVIDE Role --> API Security + Roles specification

        userService.saveUser(newUser); // instead of createUser(User usr, Role rl) !! -- delete this method
        URI location = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/api/user/{id}").build()
                .expand(newUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(newUser);
    }
    // ADD changePassword() method?
}
