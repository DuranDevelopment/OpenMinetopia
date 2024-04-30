package nl.openminetopia.module.banking.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import nl.openminetopia.api.banking.accounts.Account;
import nl.openminetopia.api.banking.manager.BankingManager;
import nl.openminetopia.utils.MessageUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@CommandAlias("rekening|ohrekening|spaarrekening|bankaccount")
public class RekeningRemoveUserCommand extends BaseCommand {

    @Subcommand("removeuser")
    @CommandPermission("openminetopia.bankaccount.removeuser")
    @Description("Verwijder een gebruiker van een bankrekening.")
    public void removeUserCommand(Player player, OfflinePlayer target, int id) {
        Account account = BankingManager.getInstance().getAccountById(id);
        if (account == null) {
            player.sendMessage(MessageUtils.format("<red>Rekening met ID: " + id + " bestaat niet."));
            return;
        }

        BankingManager.getInstance().removeUser(account, target.getUniqueId());
        player.sendMessage(MessageUtils.format("<dark_aqua>Succesvol speler <aqua>" + target.getName() + "<dark_aqua> verwijderd van de rekening met ID: <aqua>" + id));
    }
}
