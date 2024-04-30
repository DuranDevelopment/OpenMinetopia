package nl.openminetopia.module.banking.listeners;

import nl.openminetopia.api.banking.events.ATMOpenEvent;
import nl.openminetopia.module.banking.menus.AccountTypeMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getClickedBlock() == null) return;
        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;

        if (event.getClickedBlock().getType() == Material.RED_SANDSTONE_STAIRS) {
            event.setCancelled(true);

            ATMOpenEvent atmOpenEvent = new ATMOpenEvent(player);
            Bukkit.getPluginManager().callEvent(atmOpenEvent);

            if (atmOpenEvent.isCancelled()) return;
            new AccountTypeMenu(player).open(player);
        }
    }
}
