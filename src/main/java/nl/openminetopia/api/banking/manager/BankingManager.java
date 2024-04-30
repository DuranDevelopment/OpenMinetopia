package nl.openminetopia.api.banking.manager;

import com.craftmend.storm.api.enums.Where;
import lombok.Getter;
import nl.openminetopia.OpenMinetopia;
import nl.openminetopia.api.banking.accounts.Account;
import nl.openminetopia.api.banking.enums.AccountPermission;
import nl.openminetopia.api.banking.enums.AccountType;
import nl.openminetopia.module.data.database.StormDatabase;
import nl.openminetopia.module.data.database.models.BankAccountModel;
import nl.openminetopia.module.data.database.models.BankAccountUserModel;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Getter
public class BankingManager {

    private static BankingManager instance;

    public List<Account> accounts = new ArrayList<>();

    private final ExecutorService executorService = StormDatabase.getExecutorService();

    public static BankingManager getInstance() {
        if (instance == null) {
            instance = new BankingManager();
        }
        return instance;
    }

    public CompletableFuture<Account> createAccount(AccountType accountType) {
        CompletableFuture<Account> completableFuture = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
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

            Account account = new Account(id, accountType, "<gray>ID: " + id, 0, false, new HashMap<>());
            accounts.add(account);

            StormDatabase.getInstance().saveStormModel(bankAccountModel);
            completableFuture.complete(account);
        }, executorService);
        return completableFuture;
    }

    public CompletableFuture<Void> deleteAccount(Account account) {
        accounts.remove(account);

        return CompletableFuture.runAsync(() -> {
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
        }, executorService);
    }

    public CompletableFuture<Void> addUser(Account account, UUID uuid, AccountPermission permission) {
        account.getUsers().put(uuid, permission);

        return CompletableFuture.runAsync(() -> {
            try {
                BankAccountUserModel bankAccountUserModel = new BankAccountUserModel();
                bankAccountUserModel.setAccountId(account.getId());
                bankAccountUserModel.setUuid(uuid);
                bankAccountUserModel.setAccountPermission(permission.name());
                StormDatabase.getInstance().saveStormModel(bankAccountUserModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, executorService);
    }

    public CompletableFuture<Void> setName(Account account, String name) {
        account.setName(name);

        return CompletableFuture.runAsync(() -> {
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
        }, executorService);
    }

    public CompletableFuture<Void> freeze(Account account, boolean freeze) {
        account.setFrozen(freeze);

        return CompletableFuture.runAsync(() -> {
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
        }, executorService);
    }

    public CompletableFuture<Void> setBalance(Account account, double balance) {
        account.setBalance(balance);

        return CompletableFuture.runAsync(() -> {
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
        }, executorService);
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

    public CompletableFuture<Void> removeUser(Account account, UUID uuid) {
        account.getUsers().remove(uuid);

        return CompletableFuture.runAsync(() -> {
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
        }, executorService);
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
