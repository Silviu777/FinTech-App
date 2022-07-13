package com.fintech.service.implementation;

import com.fintech.config.JwtTokenUtil;
import com.fintech.dto.OperationsCodes;
import com.fintech.exception.BadRequestException;
import com.fintech.model.EmailNotification;
import com.fintech.model.User;
import com.fintech.model.VerificationToken;
import com.fintech.repository.UserRepository;
import com.fintech.repository.VerificationTokenRepository;
import com.fintech.service.AccountService;
import com.fintech.service.MailContentBuilder;
import com.fintech.service.MailService;
import com.fintech.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.fintech.utils.Constants.ACTIVATION_EMAIL;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MailContentBuilder mailContentBuilder;

    @Autowired
    private MailService mailService;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

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
        String token = generateVerificationToken(user);
        String message = mailContentBuilder.build("Thank you for signing up to One FinTech! Please click on the link below to activate your account : "
                + ACTIVATION_EMAIL + "/" + token);

        mailService.sendMail(new EmailNotification("Activate your account", user.getEmailAddress(), message));
        return accountService.createAccount(user);
    }

    @Override
    public String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(Instant.now().plusMillis(900000));

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        verificationTokenOptional.orElseThrow(() -> new RuntimeException("Invalid Token"));
        fetchUserAndEnable(verificationTokenOptional.get());
    }

    @Transactional
    void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findUserByUsername(username);

        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public String updateUser(User user) {
        User currentUser = userRepository.findByUsername(user.getUsername());
        if (currentUser != null) {
            user.setId(currentUser.getId());
            user.setFirstName(currentUser.getFirstName());
            user.setLastName(currentUser.getLastName());
            user.setEmailAddress(currentUser.getEmailAddress());
            user.setPhoneNumber(currentUser.getPhoneNumber());

            userRepository.save(user);
            return OperationsCodes.USER_UPDATED;

        } else {
            throw new RuntimeException("User " + currentUser + " not found!");
        }

    }
}
