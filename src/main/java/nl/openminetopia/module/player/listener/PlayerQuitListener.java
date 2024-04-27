package nl.openminetopia.module.player.listener;

import nl.openminetopia.module.data.database.StormDatabase;
import nl.openminetopia.module.data.database.models.PlayerModel;
import nl.openminetopia.module.player.manager.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        PlayerModel playerModel = PlayerManager.getMinetopiaPlayer(player).getPlayerModel();
        StormDatabase.getInstance().saveStormModel(playerModel);
    }
}
