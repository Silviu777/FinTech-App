package com.fintech.repository;

import com.fintech.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);
    User findUserByUserName(String username);
    Optional<User> findUserByFirstName(String firstName);
    Optional<User> findUserByLastName(String lastName);

}
