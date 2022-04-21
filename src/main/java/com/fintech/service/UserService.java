package com.fintech.service;

import com.fintech.dto.UserDTO;
import com.fintech.model.User;
import com.fintech.model.enums.Role;

public interface UserService {

    User findById(Long id);
    User findByUsername(String username);
    // check profile details!
    User saveUser(User user);
    User createUser(User user, Role role); // or registerUser?
    void deleteUser(User user);
    boolean hasRole(User user, Role role); // review!
    boolean checkEmailAddress(String emailAddress);
    User updateUser(User user);

}
