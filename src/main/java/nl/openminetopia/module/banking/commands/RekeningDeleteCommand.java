package nl.openminetopia.module.banking.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import nl.openminetopia.api.banking.accounts.Account;
import nl.openminetopia.api.banking.manager.BankingManager;
import nl.openminetopia.utils.MessageUtils;
import org.bukkit.entity.Player;

@CommandAlias("rekening|ohrekening|spaarrekening|bankaccount")
public class RekeningDeleteCommand extends BaseCommand {

    @Subcommand("delete")
    @CommandPermission("openminetopia.bankaccount.delete")
    @Description("Verwijder een bankrekening.")
    public void deleteCommand(Player player, int id) {
        Account account = BankingManager.getInstance().getAccountById(id);
        if (account == null) {
            player.sendMessage(MessageUtils.format("<red>Rekening met ID: " + id + " bestaat niet."));
            return;
        }

        BankingManager.getInstance().deleteAccount(account).whenComplete((aVoid, throwable) -> {
            if (throwable != null) {
                player.sendMessage(MessageUtils.format("<red>Er is een fout opgetreden tijdens het verwijderen van de rekening."));
                throwable.printStackTrace();
                return;
            }
            player.sendMessage(MessageUtils.format("<dark_aqua>Rekening met ID: <aqua>" + id + " <dark_aqua>is succesvol verwijderd."));
        });
    }
}
