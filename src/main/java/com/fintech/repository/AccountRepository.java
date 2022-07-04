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

    Account findAccountByIban(String iban);

    Account findAccountByAccountType(AccountType type);

    List<Account> findAccountByCurrency(Currency currency);

    Account findAccountByOwner(String owner);

    Account findAccountByOwnerAndAccountType(User owner, AccountType accountType);

    void deleteAccountByOwner(User owner);

}
