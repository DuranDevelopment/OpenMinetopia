package nl.openminetopia.module.banking.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import nl.openminetopia.api.banking.accounts.Account;
import nl.openminetopia.api.banking.enums.AccountPermission;
import nl.openminetopia.api.banking.manager.BankingManager;
import nl.openminetopia.utils.MessageUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@CommandAlias("rekening|ohrekening|spaarrekening|bankaccount")
public class RekeningAddUserCommand extends BaseCommand {

    @Subcommand("adduser")
    @CommandPermission("openminetopia.bankaccount.adduser")
    @Description("Voeg een gebruiker toe aan een bankrekening.")
    public void adduserCommand(Player player, OfflinePlayer target, int id, @Optional @Default("ADMIN") AccountPermission permission) {
        Account account = BankingManager.getInstance().getAccountById(id);
        if (target.getPlayer() == null) {
            player.sendMessage(MessageUtils.format("<red>Speler niet gevonden."));
            return;
        }
        if (account == null) {
            player.sendMessage(MessageUtils.format("<dark_aqua>De rekening met ID: " + id + " bestaat niet."));
            return;
        }

        if (BankingManager.getInstance().getAccounts().contains(account)) {
            if (account.getUsers().containsKey(target.getUniqueId())) {
                player.sendMessage(MessageUtils.format("<dark_aqua>Speler <aqua>" + target.getName() + "<dark_aqua> is al toegevoegd aan de rekening met ID: <aqua>" + id));
                return;
            }
        }
        BankingManager.getInstance().addUser(account, target.getUniqueId(), permission).whenComplete((aVoid, throwable) -> {
            if (throwable != null) {
                player.sendMessage(MessageUtils.format("<red>Er is een fout opgetreden tijdens het toevoegen van de speler aan de rekening."));
                throwable.printStackTrace();
                return;
            }
            player.sendMessage(MessageUtils.format("<dark_aqua>Succesvol speler <aqua>" + target.getName() + "<dark_aqua> toegevoegd aan de rekening met ID: <aqua>" + id + " <dark_aqua>met de permissie: <aqua>" + permission));
        });
    }
}
