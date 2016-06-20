package io.wolfbeacon.server.service;

import io.wolfbeacon.server.model.Account;
import io.wolfbeacon.server.model.User;

import java.util.List;

public interface UserAccountManager {
    boolean updateAccount(Account account);

    boolean updateUser(User user);

    Account getEnabledAccount(String naturalId);

    User getUser(Long id);

    Account getAccountByNaturalId(String naturalId);

    Long createNewUserAccount(Account account);

    List<Account> getEnabledAccounts();

    List<Account> getDisabledAccounts();

    boolean deactivateAccountByNaturalId(String naturalId);

    Account closeAccount(String naturalId);

    Account deleteAccountByNaturalId(String naturalId);
}
