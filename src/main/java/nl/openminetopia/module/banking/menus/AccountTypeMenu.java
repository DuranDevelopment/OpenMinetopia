package nl.openminetopia.module.banking.menus;

import nl.openminetopia.api.banking.accounts.Account;
import nl.openminetopia.api.banking.enums.AccountType;
import nl.openminetopia.api.banking.manager.BankingManager;
import nl.openminetopia.utils.GUIHolder;
import nl.openminetopia.utils.ItemBuilder;
import nl.openminetopia.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountTypeMenu extends GUIHolder {

    Player player;

    public AccountTypeMenu(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, 9 * 3, MessageUtils.format("<dark_aqua>Selecteer het rekeningtype: "));

        int current = 11;

        int government = 0;
        // check if player has an account of government type
        for (Account account : BankingManager.getInstance().getAccounts()) {
            if (account.getUsers() == null) continue;
            if (account.getAccountType() == AccountType.GOVERNMENT && account.getUsers().containsKey(player.getUniqueId())) government++;
        }

        if (government > 0) current = 10;

        List<AccountType> accountTypes = new ArrayList<>(Arrays.asList(AccountType.values()));
        if (government == 0) accountTypes.remove(AccountType.GOVERNMENT);

        for (AccountType accountType : accountTypes) {
            ItemBuilder accountBuilder = new ItemBuilder(accountType.getMaterial());
            accountBuilder.setName(MessageUtils.format(accountType.getName()));
            inventory.setItem(current, accountBuilder.toItemStack());

            current += 2;
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

        if (event.getCurrentItem().getType() == Material.GOLD_BLOCK) {
            new AccountContentMenu(player, null).open(player);
            return;
        }

        if (event.getCurrentItem().getType() == Material.EMERALD_BLOCK) {
            if (BankingManager.getInstance().getAccountsOfTypeFromUser(player.getUniqueId(), AccountType.GOVERNMENT).isEmpty()) {
                player.sendMessage(MessageUtils.format("<red>Je hebt geen overheidsrekening."));
                return;
            }
            new AccountSelectionMenu(player, AccountType.GOVERNMENT).open(player);
            return;
        }

        if (event.getCurrentItem().getType() == Material.DIAMOND_BLOCK) {
            if (BankingManager.getInstance().getAccountsOfTypeFromUser(player.getUniqueId(), AccountType.COMPANY).isEmpty()) {
                player.sendMessage(MessageUtils.format("<red>Je hebt geen bedrijfsrekening."));
                return;
            }
            new AccountSelectionMenu(player, AccountType.COMPANY).open(player);
            return;
        }

        if (event.getCurrentItem().getType() == Material.REDSTONE_BLOCK) {
            if (BankingManager.getInstance().getAccountsOfTypeFromUser(player.getUniqueId(), AccountType.SAVINGS).isEmpty()) {
                player.sendMessage(MessageUtils.format("<red>Je hebt geen spaarrekening."));
                return;
            }
            new AccountSelectionMenu(player, AccountType.SAVINGS).open(player);
            return;
        }
    }
}
