package nl.openminetopia.module.banking;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import nl.openminetopia.OpenMinetopia;
import nl.openminetopia.api.banking.accounts.Account;
import nl.openminetopia.api.banking.enums.AccountType;
import nl.openminetopia.api.banking.manager.BankingManager;
import nl.openminetopia.module.Module;
import nl.openminetopia.module.banking.commands.RekeningCommand;
import nl.openminetopia.module.banking.listeners.PlayerInteractListener;
import nl.openminetopia.module.data.database.StormDatabase;
import nl.openminetopia.module.data.database.models.BankAccountModel;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Collection;

public class BankingModule extends Module {

    @Getter
    private static Economy econ = null;

    @Override
    public void enable() {

        registerListener(new PlayerInteractListener());
        registerCommand(new RekeningCommand());

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
            for (BankAccountModel accountModel : accountModels) {
                Account account = new Account(accountModel.getId(), AccountType.valueOf(accountModel.getType()), accountModel.getName(), accountModel.getBalance(), accountModel.getFrozen(), null);
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
            System.out.println("Vault not found!");
            return false;
        }
        RegisteredServiceProvider<Economy> rsp =  Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            System.out.println("No economy provider found!");
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
