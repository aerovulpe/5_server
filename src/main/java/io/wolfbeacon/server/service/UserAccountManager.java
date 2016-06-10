package io.wolfbeacon.server.service;

import io.wolfbeacon.server.model.Account;

import java.util.List;

public interface UserAccountManager {
    boolean updateAccount(Account account);

    Account getEnabledAccount(String naturalId);

    Account getAccountByNaturalId(String naturalId);

    Long createNewUserAccount(Account account);

    List<Account> getEnabledAccounts();

    List<Account> getDisabledAccounts();

    boolean deactivateAccountByNaturalId(String naturalId);

    Account closeAccount(String naturalId);

    Account deleteAccountByNaturalId(String naturalId);
}
