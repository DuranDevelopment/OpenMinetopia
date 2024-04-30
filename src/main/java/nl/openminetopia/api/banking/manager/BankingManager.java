package nl.openminetopia.api.banking.manager;

import com.craftmend.storm.api.enums.Where;
import lombok.Getter;
import nl.openminetopia.api.banking.accounts.Account;
import nl.openminetopia.api.banking.enums.AccountPermission;
import nl.openminetopia.api.banking.enums.AccountType;
import nl.openminetopia.module.data.database.StormDatabase;
import nl.openminetopia.module.data.database.models.BankAccountModel;
import nl.openminetopia.module.data.database.models.BankAccountUserModel;

import java.util.*;

@Getter
public class BankingManager {

    private static BankingManager instance;

    public List<Account> accounts = new ArrayList<>();

    public static BankingManager getInstance() {
        if (instance == null) {
            instance = new BankingManager();
        }
        return instance;
    }

    public Account createAccount(AccountType accountType) {
        int id = 0;
        try {
            id = StormDatabase.getInstance().getStorm().buildQuery(BankAccountModel.class)
                    .execute()
                    .join()
                    .size() + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        BankAccountModel bankAccountModel = new BankAccountModel();
        bankAccountModel.setType(accountType);
        bankAccountModel.setBalance(0.0);
        bankAccountModel.setFrozen(false);
        bankAccountModel.setName("<gray>ID: " + id);

        Account account = new Account(id, accountType, "<gray>ID: ", 0, false, null);
        accounts.add(account);

        StormDatabase.getInstance().saveStormModel(bankAccountModel);

        return account;
    }

    public void deleteAccount(Account account) {
        accounts.remove(account);

        try {
            Collection<BankAccountModel> accountModel =
                    StormDatabase.getInstance().getStorm().buildQuery(BankAccountModel.class)
                            .where("id", Where.EQUAL, account.getId())
                            .limit(1)
                            .execute()
                            .join();
            StormDatabase.getInstance().getStorm().delete(accountModel.iterator().next());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUser(Account account, UUID uuid, AccountPermission permission) {
        account.getUsers().put(uuid, permission);

        BankAccountUserModel bankAccountUserModel = new BankAccountUserModel();
        bankAccountUserModel.setAccountId(account.getId());
        bankAccountUserModel.setUuid(uuid);
        bankAccountUserModel.setAccountPermission(permission.name());
        StormDatabase.getInstance().saveStormModel(bankAccountUserModel);
    }

    public void setName(Account account, String name) {
        account.setName(name);

        try {
            Collection<BankAccountModel> accountModel =
                    StormDatabase.getInstance().getStorm().buildQuery(BankAccountModel.class)
                            .where("id", Where.EQUAL, account.getId())
                            .limit(1)
                            .execute()
                            .join();
            BankAccountModel bankAccountModel = accountModel.iterator().next();
            bankAccountModel.setName(name);
            StormDatabase.getInstance().saveStormModel(bankAccountModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void freeze(Account account, boolean freeze) {
        account.setFrozen(freeze);

        try {
            Collection<BankAccountModel> accountModel =
                    StormDatabase.getInstance().getStorm().buildQuery(BankAccountModel.class)
                            .where("id", Where.EQUAL, account.getId())
                            .limit(1)
                            .execute()
                            .join();
            BankAccountModel bankAccountModel = accountModel.iterator().next();
            bankAccountModel.setFrozen(freeze);
            StormDatabase.getInstance().saveStormModel(bankAccountModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBalance(Account account, double balance) {
        account.setBalance(balance);

        try {
            Collection<BankAccountModel> accountModel =
                    StormDatabase.getInstance().getStorm().buildQuery(BankAccountModel.class)
                            .where("id", Where.EQUAL, account.getId())
                            .limit(1)
                            .execute()
                            .join();
            BankAccountModel bankAccountModel = accountModel.iterator().next();
            bankAccountModel.setBalance(balance);
            StormDatabase.getInstance().saveStormModel(bankAccountModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Account> getAccountsFromUser(UUID uuid) {
        List<Account> accounts = new ArrayList<>();
        for (Account account : this.accounts) {
            if (account.getUsers().containsKey(uuid)) {
                accounts.add(account);
            }
        }
        return accounts;
    }

    public void removeUser(Account account, UUID uuid) {
        account.getUsers().remove(uuid);

        try {
            Collection<BankAccountUserModel> accountUserModel =
                    StormDatabase.getInstance().getStorm().buildQuery(BankAccountUserModel.class)
                            .where("accountId", Where.EQUAL, account.getId())
                            .where("uuid", Where.EQUAL, uuid)
                            .limit(1)
                            .execute()
                            .join();
            StormDatabase.getInstance().getStorm().delete(accountUserModel.iterator().next());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Account> getAccountsOfTypeFromUser(UUID uuid, AccountType accountType) {
        List<Account> accounts = new ArrayList<>();
        for (Account account : this.accounts) {
            if (account.getUsers().containsKey(uuid) && account.getAccountType() == accountType) {
                accounts.add(account);
            }
        }
        return accounts;
    }

    public List<Account> getAccountsOfType(AccountType accountType) {
        List<Account> accounts = new ArrayList<>();
        for (Account account : this.accounts) {
            if (account.getAccountType() == accountType) {
                accounts.add(account);
            }
        }
        return accounts;
    }

    public AccountPermission getAccountPermission(Account account, UUID uuid) {
        return account.getUsers().get(uuid);
    }


    public Account getAccountById(int id) {
        return accounts.stream().filter(account -> account.getId() == id).findFirst().orElse(null);
    }
}
