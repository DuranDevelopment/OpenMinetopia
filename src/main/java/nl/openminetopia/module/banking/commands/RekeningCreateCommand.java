package nl.openminetopia.module.banking.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import nl.openminetopia.api.banking.accounts.Account;
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
        Account account;
        switch (type.name().toLowerCase()) {
            case "savings":
                account = BankingManager.getInstance().createAccount(AccountType.SAVINGS);
                player.sendMessage(MessageUtils.format("<dark_aqua>Succesvol een spaarrekening aangemaakt met ID: " + account.getId() + "."));
                break;
            case "spaar":
            case "company":
                account = BankingManager.getInstance().createAccount(AccountType.COMPANY);
                player.sendMessage(MessageUtils.format("<dark_aqua>Succesvol een bedrijfsrekening aangemaakt met ID: " + account.getId() + "."));
                break;
            case "bedrijf":
            case "government":
                account = BankingManager.getInstance().createAccount(AccountType.GOVERNMENT);
                player.sendMessage(MessageUtils.format("<dark_aqua>Succesvol een overheid rekening aangemaakt met ID: " + account.getId() + "."));
                break;
            case "overheid":
            default:
                player.sendMessage(MessageUtils.format("<red>Geef een geldig type op. (Spaar/Bedrijf/Overheid)"));
                break;
        }
    }
}
