package nl.openminetopia.module.banking.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import nl.openminetopia.api.banking.enums.AccountType;
import nl.openminetopia.api.banking.manager.BankingManager;
import nl.openminetopia.utils.MessageUtils;
import org.bukkit.entity.Player;

@CommandAlias("rekening|ohrekening|spaarrekening|bankaccount")
public class RekeningCreateCommand extends BaseCommand {

    @Subcommand("create")
    @CommandPermission("openminetopia.bankaccount.create")
    @Description("Maak een bankrekening.")
    public void createCommand(Player player, AccountType type) {
        if (type == AccountType.PRIVATE) {
            player.sendMessage(MessageUtils.format("<red>Je kan geen privé rekening aanmaken."));
            return;
        }

        BankingManager.getInstance().createAccount(type).whenComplete((account, throwable) -> {
            if (throwable != null) {
                player.sendMessage(MessageUtils.format("<red>Er is een fout opgetreden tijdens het aanmaken van de rekening."));
                throwable.printStackTrace();
                return;
            }
            player.sendMessage(MessageUtils.format("<dark_aqua>Je hebt een nieuwe " + type + " rekening aangemaakt met ID: <aqua>" + account.getId()));
        });
    }
}
