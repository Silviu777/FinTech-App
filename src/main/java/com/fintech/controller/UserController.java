package com.fintech.controller;

import com.fintech.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    @GetMapping
    public String test() {
        return "<body>\n" + "Working!\n" + "</body>\n" + "</html>";
    }
}
