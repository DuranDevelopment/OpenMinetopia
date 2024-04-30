package nl.openminetopia.module.banking.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import nl.openminetopia.api.banking.accounts.Account;
import nl.openminetopia.api.banking.enums.AccountPermission;
import nl.openminetopia.api.banking.enums.AccountType;
import nl.openminetopia.api.banking.manager.BankingManager;
import nl.openminetopia.utils.MessageUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.List;

@CommandAlias("rekening|ohrekening|spaarrekening|bankaccount")
public class RekeningPListCommand extends BaseCommand {

    @Subcommand("plist")
    @CommandPermission("openminetopia.bankaccount.plist")
    @Description("Krijg een lijst van bankrekeningen.")
    public void plistCommand(Player player, OfflinePlayer target) {

        List<Account> sortedAccounts = BankingManager.getInstance().getAccountsFromUser(target.getUniqueId()).stream()
                .sorted(Comparator.comparing(Account::getId))
                .toList();

        for (Account account : sortedAccounts) {
            AccountPermission permission = BankingManager.getInstance().getAccountPermission(account, target.getUniqueId());
            player.sendMessage(MessageUtils.format("<dark_aqua>" + account.getId() + ". <aqua>" + account.getName() + " <dark_aqua>(<aqua>" + account.getFormattedBalance() + "<dark_aqua>) <red>" + permission.toString()));
        }
    }
}
