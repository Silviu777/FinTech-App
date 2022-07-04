package com.fintech.service;

import com.fintech.model.User;
import com.fintech.model.enums.Role;

public interface UserService {

    User findById(Long id);

    User findByUsername(String username);

    User saveUser(User user);

    User createUser(User user, Role role);

    void deleteUser(User user);

    Role findRoleName(Role role);

    boolean hasRole(User user, Role role); // review!

    boolean checkEmailAddress(String emailAddress);

    User updateUser(User user);

}
