package com.fintech.repository;

import com.fintech.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);

    Optional<User> findUserByUserName(String username);

    Optional<User> findUserByFirstName(String firstName);

    Optional<User> findUserByLastName(String lastName);

    Optional<User> findUserByEmailAddress(String emailAddress);

//    @Transactional
//    @Modifying
//    @Query("UPDATE User a " +
//            "SET a.enabled = TRUE WHERE a.emailAddress = ?1")
//    int enableAppUser(String email);
}
