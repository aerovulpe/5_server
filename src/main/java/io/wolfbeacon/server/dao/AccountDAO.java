package io.wolfbeacon.server.dao;

import io.wolfbeacon.server.model.Account;

import java.util.List;

public interface AccountDAO extends GenericDAO<Account, Long> {

    List<Account> findAll();

    List<Account> findEnabled();

    List<Account> findDisabled();

    Account getAccount(String naturalId);
}