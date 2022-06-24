//package com.fintech.appuser;
//
//import com.fintech.model.User;
//import com.fintech.registration.token.ConfirmationToken;
//import com.fintech.registration.token.ConfirmationTokenService;
//import com.fintech.repository.UserRepository;
//import lombok.AllArgsConstructor;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//@AllArgsConstructor
//public class AppUserService implements UserDetailsService {
//
//    private final static String USER_NOT_FOUND_EXCEPTION =
//            "User with email %s is not registered!";
//
//    private final UserRepository userRepository;
//
//    private final ConfirmationTokenService confirmationTokenService;
//
//    private final BCryptPasswordEncoder passwordEncoder;
//
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return userRepository.findUserByEmailAddress(email)
//                .orElseThrow( () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_EXCEPTION, email)));
//    }
//
//    public Optional<User> loadUserByUserName(String username) throws UsernameNotFoundException {
//        return Optional.ofNullable(userRepository.findUserByUserName(username));
//    }
//
//    public String signUp(User user) {
//        boolean isUserRegistered = userRepository.findUserByEmailAddress(user.getEmailAddress()).isPresent();
//
//        if (isUserRegistered) {
//            throw new IllegalStateException("The email address you introduces is already taken!");
//        }
//
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);
//        userRepository.save(user);
//
//        String token = UUID.randomUUID().toString();
//        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
//        confirmationTokenService.saveConfirmationToken(confirmationToken);
//
//        return token;
//    }
//
//    public int enableAppUser(String email) {
//        return userRepository.enableAppUser(email);
//    }
//}
