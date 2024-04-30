package nl.openminetopia.module.banking.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import nl.openminetopia.api.banking.accounts.Account;
import nl.openminetopia.api.banking.enums.AccountType;
import nl.openminetopia.api.banking.manager.BankingManager;
import nl.openminetopia.utils.MessageUtils;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@CommandAlias("rekening|ohrekening|spaarrekening|bankaccount")
public class RekeningListCommand extends BaseCommand {

    @Subcommand("list")
    @CommandPermission("openminetopia.bankaccount.create")
    @Description("Krijg een lijst van bankrekeningen.")
    public void listCommand(Player player, AccountType type, @Optional @Default("1") int page) {


        int amount = BankingManager.getInstance().getAccountsOfType(type).size();
        int pages = amount / 10 + 1;

        if (page < 1 || page > pages) {
            player.sendMessage(MessageUtils.format("<red>Deze pagina bestaat niet."));
            return;
        }
        player.sendMessage(MessageUtils.format("<dark_aqua>Er zijn <aqua>" + amount + "<dark_aqua> rekeningen"));

        List<Account> sortedAccounts = BankingManager.getInstance().getAccountsOfType(type).stream()
                .sorted(Comparator.comparing(Account::getId))
                .toList();

        int i = 0;
        for (Account account : sortedAccounts) {
            if (i < (page - 1) * 10 || i >= page * 10) {
                i++;
                continue;
            }
            player.sendMessage(MessageUtils.format("<dark_aqua>" + account.getId() + ". <aqua>" + account.getName() + " <dark_aqua>(<aqua>" + account.getFormattedBalance() + "<dark_aqua>)"));
            i++;
        }
        player.sendMessage(MessageUtils.format("<aqua>Pagina <dark_aqua>" + page + "<aqua>/<dark_aqua>" + pages));
    }
}
