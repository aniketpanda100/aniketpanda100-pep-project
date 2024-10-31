package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;
    /**
     * no-args constructor for creating a new AccountService with a new AccountDAO.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /**
     * Constructor for a AccountService when a AccountDAO is provided.
     * This is used for when a mock AccountDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AccountService independently of AccountDAO.
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    /**
     * Uses the AccountDAO to persist an account. The given Account will not have an id provided.
     *
     * @param account an account object.
     * @return The persisted account if the persistence is successful.
     */
    public Account addAccount(Account account) {
        String user = account.getUsername();
        String pass = account.getPassword();
        if (user == null || pass == null) return null;
        if (user.length() < 1 || pass.length() < 4) return null;
        if (accountDAO.getAccountByUsername(account.getUsername())!=null) return null;

        return accountDAO.insertAccount(account);
    }

}
