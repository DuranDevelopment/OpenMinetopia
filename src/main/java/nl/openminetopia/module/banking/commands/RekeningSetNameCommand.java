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
public class RekeningSetNameCommand extends BaseCommand {

    @Subcommand("setname")
    @CommandPermission("openminetopia.bankaccount.setname")
    @Description("Hernoem een bankrekening.")
    public void setNameCommand(Player player, int id, String name) {
        Account account = BankingManager.getInstance().getAccountById(id);
        if (account == null) {
            player.sendMessage(MessageUtils.format("<red>Rekening met ID: " + id + " bestaat niet."));
            return;
        }

        BankingManager.getInstance().setName(account, name);
        player.sendMessage(MessageUtils.format("<dark_aqua>Rekening met ID: <aqua>" + id + " <dark_aqua>is succesvol hernoemd naar <aqua>" + name + "<dark_aqua>."));
    }
}
