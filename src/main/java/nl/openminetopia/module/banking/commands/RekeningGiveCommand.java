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
public class RekeningGiveCommand extends BaseCommand {

    @Subcommand("give")
    @CommandPermission("openminetopia.bankaccount.give")
    @Description("Voeg geld toe aan het saldo van een rekening.")
    public void giveCommand(Player player, int id, double amount) {
        Account account = BankingManager.getInstance().getAccountById(id);
        if (account == null) {
            player.sendMessage(MessageUtils.format("<red>Rekening met ID: " + id + " bestaat niet."));
            return;
        }

        BankingManager.getInstance().setBalance(account, account.getBalance() + amount).whenComplete((aVoid, throwable) -> {
            if (throwable != null) {
                player.sendMessage(MessageUtils.format("<red>Er is een fout opgetreden tijdens het toevoegen van geld aan de rekening."));
                throwable.printStackTrace();
                return;
            }
            player.sendMessage(MessageUtils.format("<dark_aqua>Succesvol <aqua>€" + amount + "<dark_aqua> toegevoegd aan de rekening met ID: <aqua>" + id));
        });
    }
}
