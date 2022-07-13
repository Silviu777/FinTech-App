package com.fintech.service;

import com.fintech.model.Account;
import com.fintech.model.User;
import com.fintech.model.enums.Role;

public interface UserService {

    User findById(Long id);

    User getUserFromToken(String token);

    String createUser(User user);

    String updateUser(User user);

    String generateVerificationToken(User user);

}
