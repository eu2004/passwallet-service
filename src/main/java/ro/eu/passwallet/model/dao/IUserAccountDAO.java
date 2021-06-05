package ro.eu.passwallet.model.dao;

import ro.eu.passwallet.model.UserAccount;

import java.util.Collection;

public interface IUserAccountDAO {
    UserAccount findUserAccountById(Integer id);

    Collection<UserAccount> findAllUsersAccounts();

    Collection<UserAccount> findUsersAccountsByName(String name);

    Integer createUserAccount(UserAccount userAccount);

    boolean deleteUserAccountById(Integer id);

    boolean updateUserAccount(UserAccount userAccount);
}
