package nl.openminetopia.module.banking;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import nl.openminetopia.OpenMinetopia;
import nl.openminetopia.api.banking.accounts.Account;
import nl.openminetopia.api.banking.enums.AccountPermission;
import nl.openminetopia.api.banking.enums.AccountType;
import nl.openminetopia.api.banking.manager.BankingManager;
import nl.openminetopia.module.Module;
import nl.openminetopia.module.banking.commands.*;
import nl.openminetopia.module.banking.listeners.PlayerInteractListener;
import nl.openminetopia.module.data.database.StormDatabase;
import nl.openminetopia.module.data.database.models.BankAccountModel;
import nl.openminetopia.module.data.database.models.BankAccountUserModel;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.*;

public class BankingModule extends Module {

    @Getter
    private static Economy econ = null;

    @Override
    public void enable() {

        // Register listeners
        registerListener(new PlayerInteractListener());

        // Register commands
        registerCommand(new RekeningCommand());
        registerCommand(new RekeningCreateCommand());
        registerCommand(new RekeningDeleteCommand());
        registerCommand(new RekeningFreezeCommand());
        registerCommand(new RekeningGiveCommand());
        registerCommand(new RekeningInfoCommand());
        registerCommand(new RekeningListCommand());
        registerCommand(new RekeningPListCommand());
        registerCommand(new RekeningAddUserCommand());
        registerCommand(new RekeningRemoveUserCommand());
        registerCommand(new RekeningSetNameCommand());

        if (!setupEconomy()) {
            OpenMinetopia.getInstance().getLogger().severe("- Disabled due to no Vault dependency found!");
            Bukkit.getServer().getPluginManager().disablePlugin(OpenMinetopia.getInstance());
            return;
        }

        try {
            Collection<BankAccountModel> accountModels =
                    StormDatabase.getInstance().getStorm().buildQuery(BankAccountModel.class)
                            .execute()
                            .join();

            Collection<BankAccountUserModel> accountUserModels =
                    StormDatabase.getInstance().getStorm().buildQuery(BankAccountUserModel.class)
                            .execute()
                            .join();
            for (BankAccountModel accountModel : accountModels) {
                Map<UUID, AccountPermission> users = new HashMap<>();

                for (BankAccountUserModel accountUserModel : accountUserModels) {
                    if (Objects.equals(accountUserModel.getAccountId(), accountModel.getId())) {
                        users.put(accountUserModel.getUuid(), AccountPermission.valueOf(accountUserModel.getAccountPermission()));
                    }
                }
                Account account = new Account(accountModel.getId(), accountModel.getType(), accountModel.getName(), accountModel.getBalance(), accountModel.getFrozen(), users);
                BankingManager.getInstance().getAccounts().add(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disable() {

    }

    private boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp =  Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            OpenMinetopia.getInstance().getLogger().severe("No economy provider found! (have you installed an economy plugin such as Essentials?)" );
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
