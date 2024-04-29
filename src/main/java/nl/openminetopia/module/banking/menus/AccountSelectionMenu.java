package nl.openminetopia.module.banking.menus;

import nl.openminetopia.api.banking.enums.AccountType;
import nl.openminetopia.utils.GUIHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AccountSelectionMenu extends GUIHolder {

    private final Player player;

    public AccountSelectionMenu(Player player, AccountType accountType) {
        this.player = player;


    }

    @Override
    public void onClick(InventoryClickEvent event) {

    }
}
