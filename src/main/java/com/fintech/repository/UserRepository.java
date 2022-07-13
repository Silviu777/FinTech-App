package com.fintech.repository;

import com.fintech.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

    User findByUsername(String username);

    Optional<User> findUserByEmailAddress(String emailAddress);

}
