package com.fintech.repository;

import com.fintech.model.Account;
import com.fintech.model.User;
import com.fintech.model.enums.AccountType;
import com.fintech.model.enums.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAll();

    Account findAccountById(Long id);

    Account findByAccountNo(String accountNo);

    Account findByIban(String iban);

    Account findAccountByOwner(String owner);

    Account findAccountByOwnerId(Long ownerId);

}
