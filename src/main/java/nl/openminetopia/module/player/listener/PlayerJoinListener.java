package nl.openminetopia.module.player.listener;

import nl.openminetopia.api.player.MinetopiaPlayer;
import nl.openminetopia.module.data.database.models.PlayerModel;
import nl.openminetopia.module.player.manager.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        MinetopiaPlayer minetopiaPlayer = PlayerManager.getMinetopiaPlayer(player);
        PlayerModel playerModel = minetopiaPlayer.getPlayerModel();
        player.sendMessage("Welcome back, " + playerModel.getUniqueId() + "!");
    }
}
