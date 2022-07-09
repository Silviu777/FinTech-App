package com.fintech.service.implementation;

import com.fintech.config.JwtTokenUtil;
import com.fintech.dto.OperationsCodes;
import com.fintech.exception.BadRequestException;
import com.fintech.model.User;
import com.fintech.model.enums.Role;
import com.fintech.repository.UserRepository;
import com.fintech.service.AccountService;
import com.fintech.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final Logger logger = LogManager.getLogger(getClass());


    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found!"));
    }

    @Override
    public User getUserFromToken(String token) {
        String contactNo = jwtTokenUtil.getUsernameFromToken(token);
        return userRepository.findUserByUsername(contactNo);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public String createUser(User user) {
        if (userRepository.findUserByUsername(user.getUsername()) != null) {
            logger.info("User with username " +  user.getUsername() + "already exists!");
            throw new BadRequestException(OperationsCodes.USER_EXIST);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setUsername(user.getUsername());
        user.setEmailAddress(user.getEmailAddress());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setCreatedDate(Instant.now());

        userRepository.save(user);
        return accountService.createAccount(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public Role findRoleName(Role role) { // delete? wait for integration of auth0
        Role selectedRole;

        switch (role) {

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
    public String updateUser(User user) {
        User existingUser = userRepository.findUserByUsername(user.getUsername());
        if (existingUser == null) {
            throw new RuntimeException("User " + existingUser + " not found!");
        }

        user.setId(existingUser.getId());
        user.setFirstName(existingUser.getFirstName());
        user.setLastName(existingUser.getLastName());
        user.setUsername(existingUser.getUsername());
        user.setEmailAddress(existingUser.getEmailAddress());
        user.setPhoneNumber(existingUser.getPhoneNumber());
        user.setAddress(existingUser.getAddress());
        user.setCity(existingUser.getCity());
        user.setCountry(existingUser.getCountry());

        userRepository.save(user);
        return OperationsCodes.USER_UPDATED;
    }
}
