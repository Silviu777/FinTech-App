package com.fintech.service;

import com.fintech.dto.AuthenticationResponse;
import com.fintech.dto.RefreshTokenRequest;
import com.fintech.dto.RegisterRequest;
import com.fintech.model.EmailNotification;
import com.fintech.model.User;
import com.fintech.model.VerificationToken;
import com.fintech.repository.UserRepository;
import com.fintech.repository.VerificationTokenRepository;
import com.fintech.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

import static com.fintech.utils.Constants.ACTIVATION_EMAIL;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final MailContentBuilder mailContentBuilder;

    private final MailService mailService;

    private final VerificationTokenRepository verificationTokenRepository;


    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setUsername(registerRequest.getUsername());
        user.setEmailAddress(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setCreatedDate(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        String message = mailContentBuilder.build("Thank you for signing up to One FinTech! Please click on the link below to activate your account : "
                + ACTIVATION_EMAIL + "/" + token);

        mailService.sendMail(new EmailNotification("Activate your account", user.getEmailAddress(), message));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(Instant.now().plusMillis(900000));

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
