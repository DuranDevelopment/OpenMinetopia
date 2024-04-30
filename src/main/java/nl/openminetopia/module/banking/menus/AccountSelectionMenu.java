package nl.openminetopia.module.banking.menus;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.openminetopia.api.banking.accounts.Account;
import nl.openminetopia.api.banking.enums.AccountType;
import nl.openminetopia.api.banking.manager.BankingManager;
import nl.openminetopia.utils.GUIHolder;
import nl.openminetopia.utils.ItemBuilder;
import nl.openminetopia.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class AccountSelectionMenu extends GUIHolder {

    private final Player player;

    public AccountSelectionMenu(Player player, AccountType accountType) {
        this.player = player;

        this.inventory = Bukkit.createInventory(this, 9 * 2, MessageUtils.format("<dark_aqua>Selecteer de rekening: "));
        List<Account> accounts = BankingManager.getInstance().getAccountsOfTypeFromUser(player.getUniqueId(), accountType);
        for (Account account : accounts) {
            ItemBuilder accountBuilder = new ItemBuilder(account.getAccountType().getMaterial());
            accountBuilder.setName(MessageUtils.format(account.getName())).setNBT("openmt_menu_account", String.valueOf(account.getId()));
            inventory.addItem(accountBuilder.toItemStack());
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);

        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null) return;

        int id = Integer.parseInt(NBTEditor.getString(event.getCurrentItem(), "openmt_menu_account"));
        new AccountContentMenu(player, BankingManager.getInstance().getAccountById(id)).open(player);
    }
}
