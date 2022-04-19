package com.fintech.repository;

import com.fintech.model.Account;
import com.fintech.model.User;
import com.fintech.model.enums.AccountType;
import com.fintech.model.enums.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAll();

    Account findAccountByAccountType(AccountType type);

    Account findAccountById(Long id);

    Optional<Account> findAccountByIban(String iban);

    List<Account> findAccountByCurrency(Currency currency);

    Account findAccountByOwner(User owner); // needed?

    Account findAccountByOwnerAndAccountType(User owner, AccountType accountType);

    void deleteAccountByOwner(User owner);
}
