package nl.openminetopia.module.banking.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import nl.openminetopia.api.banking.accounts.Account;
import nl.openminetopia.api.banking.manager.BankingManager;
import nl.openminetopia.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

@CommandAlias("rekening|ohrekening|spaarrekening|bankaccount")
public class RekeningInfoCommand extends BaseCommand {

    @Subcommand("info")
    @CommandPermission("openminetopia.bankaccount.info")
    @Description("Krijg informatie over een bankrekening te zien.")
    public void infoCommand(Player player, int id) {
        Account account = BankingManager.getInstance().getAccountById(id);
        if (account == null) {
            player.sendMessage(MessageUtils.format("<red>Rekening met ID: " + id + " bestaat niet."));
            return;
        }

        player.sendMessage("");
        player.sendMessage(MessageUtils.format("<dark_aqua>Rekening <aqua>" + account.getName() + " <dark_aqua>(<aqua>ID: " + account.getId() + "<dark_aqua>)"));
        player.sendMessage(MessageUtils.format("<dark_aqua>Saldo: <aqua>" + account.getFormattedBalance()));
        player.sendMessage(MessageUtils.format("<dark_aqua>Type: <aqua>" + account.getAccountType()));

        // list users with their permissions
        StringBuilder users = new StringBuilder();
        account.getUsers().forEach((uuid, permission) -> {
            users.append(Bukkit.getOfflinePlayer(uuid).getName())
                    .append(" <dark_aqua>(<aqua>")
                    .append(permission)
                    .append("<dark_aqua>)");
            users.append(", ");
        });

        player.sendMessage(MessageUtils.format("<dark_aqua>Gebruikers: <aqua>" + users));
    }
}
