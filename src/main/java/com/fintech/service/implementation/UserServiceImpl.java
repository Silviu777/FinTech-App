package com.fintech.service.implementation;

import com.fintech.model.User;
import com.fintech.model.enums.Role;
import com.fintech.repository.UserRepository;
import com.fintech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found!"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUserName(username);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User createUser(User user, Role role) {
        return null;  // to be reviewed after setting security & auth roles
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public Role findRoleName(Role role) {
        Role selectedRole;

        switch (role) {
            case CLIENT:
                selectedRole = Role.CLIENT;
            case ADMIN:
                selectedRole = Role.ADMIN;
                break;
            default:
                selectedRole = Role.CLIENT;
        }
        return selectedRole;
    }

    @Override
    public boolean hasRole(User user, Role role) {
        return false; // to be reviewed!
    }

    @Override
    public boolean checkEmailAddress(String emailAddress) {
        return userRepository.findUserByEmailAddress(emailAddress).isPresent();
    }

    @Override
    public User updateUser(User user) {
        User existingUser = userRepository.findUserById(user.getId());
        if (existingUser == null) {
            throw new RuntimeException("User " + existingUser + " not found!");
        }

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setUserName(user.getUserName());
        existingUser.setBirthDate(user.getBirthDate());
        existingUser.setEmailAddress(user.getEmailAddress());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setAddress(user.getAddress());
        existingUser.setCity(user.getCity());
        existingUser.setCountry(user.getCountry());

        userRepository.save(existingUser);
        return existingUser;
    }
}
