package com.fintech.service.implementation;

import com.fintech.dto.NewAccountDtoInput;
import com.fintech.dto.NewAccountDtoOutput;
import com.fintech.exception.NegativeBalance;
import com.fintech.model.Account;
import com.fintech.model.Transaction;
import com.fintech.model.User;
import com.fintech.model.enums.AccountType;
import com.fintech.model.enums.Currency;
import com.fintech.model.enums.TransactionStatus;
import com.fintech.repository.AccountRepository;
import com.fintech.repository.TransactionRepository;
import com.fintech.repository.UserRepository;
import com.fintech.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccount(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.orElse(null);
    }

    @Override
    public Account getAccountByUsername(String username) {
        return accountRepository.findAccountByOwner(username);
    }

    @Override
    public Account getAccountByIBAN(String iban) {
        return accountRepository.findAccountByIban(iban);
    }

    @Override
    public NewAccountDtoOutput openAccount(NewAccountDtoInput newAccountDetails, String username) {

        AccountType accountType = newAccountDetails.getAccountType();
        Currency currency = (newAccountDetails.getCurrency());
        BigDecimal balance = newAccountDetails.getBalance();

        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeBalance();
        }

        User user = userRepository.findUserByUsername(username).orElseThrow();
        Account newAccount = new Account();
        String accountIban = createIban();

        newAccount.setOwner(user);
        newAccount.setIban(accountIban);
        newAccount.setAccountType(accountType);
        newAccount.setCurrency(currency);
        newAccount.setBalance(balance);

        if (accountType.equals(AccountType.PRIMARY)) {
            newAccount.setInterestRate(3);
        }
        else {
            newAccount.setInterestRate(0.3);
        }

        newAccount.setDateOpened(new Date());

        accountRepository.save(newAccount);
        return new NewAccountDtoOutput(accountIban, username, accountType, balance, currency);

    }

    @Override
    public void deposit(Long accountId, BigDecimal amount) {
        Account account = getAccount(accountId);
        account.setBalance(account.getBalance().add(amount));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionDate(new Date());
        transaction.setAmount(amount);
        transaction.setStatus(TransactionStatus.VERIFIED);
        transaction.setCurrency(account.getCurrency());
        transaction.setDescription("Account deposit");
        transactionRepository.save(transaction);
        accountRepository.save(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void closeAccount(User user) {
        accountRepository.deleteAccountByOwner(user);
    }

    @Override
    public List<Transaction> viewAccountTransactions(Long accountId) {
        return transactionRepository.findAllByAccount(getAccount(accountId));
    }

    public String createIban() {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        String countryCode = "RO";
        builder.append(countryCode);

        int leftLimit = 65; // letter 'A'
        int rightLimit = 90; // letter 'Z'
        int targetStringLength = 4;

        String bankCode = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        int checkDigits = random.nextInt(10) + 10;
        builder.append(checkDigits).append(" ");
        builder.append(bankCode);

        for (int i = 0; i < 4; i++) {
            long bankAccountNumber = random.nextInt(9999 + 1 - 1000) + 1000;
            builder.append(" " + bankAccountNumber);
        }

        return builder.toString();
    }
}
