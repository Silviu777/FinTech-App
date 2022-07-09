package com.fintech.service;

import com.fintech.model.Account;
import com.fintech.model.User;
import com.fintech.model.enums.Role;

public interface UserService {

    User findById(Long id);

    User getUserFromToken(String token);

    User findByUsername(String username);

    User saveUser(User user);

    String createUser(User user);

    void deleteUser(User user);

    Role findRoleName(Role role);

    boolean hasRole(User user, Role role); // review!

    boolean checkEmailAddress(String emailAddress);

    String updateUser(User user);

}
