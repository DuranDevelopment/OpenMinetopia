package nl.openminetopia.api.banking.manager;

import com.craftmend.storm.api.enums.Where;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import nl.openminetopia.api.banking.accounts.Account;
import nl.openminetopia.api.banking.enums.AccountType;
import nl.openminetopia.module.data.database.StormDatabase;
import nl.openminetopia.module.data.database.models.BankAccountModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public Account createAccount(AccountType accountType, String name) {
        BankAccountModel bankAccountModel = new BankAccountModel();
        bankAccountModel.setType(accountType.getName());
        bankAccountModel.setName(name);

        StormDatabase.getInstance().saveStormModel(bankAccountModel);

        Account account = new Account(bankAccountModel.getId(), accountType, name, 0, false, null);
        accounts.add(account);

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

    public Account getAccountById(int id) {
        return accounts.stream().filter(account -> account.getId() == id).findFirst().orElse(null);
    }
}
