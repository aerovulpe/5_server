package io.wolfbeacon.server.service;

import io.wolfbeacon.server.dao.AccountDAO;
import io.wolfbeacon.server.dao.UserDAO;
import io.wolfbeacon.server.model.Account;
import io.wolfbeacon.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class UserAccountManagerImpl implements UserAccountManager {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private AccountDAO accountDAO;

    public UserAccountManagerImpl() {
    }

    @Override
    public boolean updateAccount(Account account) {
        return account != null && this.accountDAO.update(account);
    }

    @Override
    public Account getEnabledAccount(String naturalId) {
        Account account = accountDAO.getAccount(naturalId);
        return account.hasPermission(Account.PERMISSION_ENABLED) ? account : null;
    }

    @Override
    public Account getAccountByNaturalId(String naturalId) {
        return accountDAO.getAccount(naturalId);
    }

    @Override
    public Long createNewUserAccount(Account account) {
        Long id = accountDAO.create(account);
        account.setId(id);
        User user = new User();
        user.setAccount(account);
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        userDAO.create(user);
        return id;
    }

    @Override
    public List<Account> getEnabledAccounts() {
        return accountDAO.findEnabled();
    }

    @Override
    public List<Account> getDisabledAccounts() {
        return accountDAO.findDisabled();
    }

    @Override
    public boolean deactivateAccountByNaturalId(String naturalId) {
        Account account = accountDAO.getAccount(naturalId);
        account.removePermission(Account.PERMISSION_ENABLED);
        return accountDAO.update(account);
    }

    @Override
    public Account closeAccount(String naturalId) {
        Account account = accountDAO.getAccount(naturalId);
        account.getPermissions().clear();
        account.getRoles().clear();
        return accountDAO.update(account) ? account : null;
    }

    @Override
    public Account deleteAccountByNaturalId(String naturalId) {
        Account account = accountDAO.getAccount(naturalId);
        return accountDAO.delete(account) ? account : null;
    }
}
