package nl.openminetopia.module.scoreboard.listeners;

import fr.mrmicky.fastboard.FastBoard;
import nl.openminetopia.OpenMinetopia;
import nl.openminetopia.module.scoreboard.ScoreboardModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private ScoreboardModule scoreboardModule = OpenMinetopia.getModuleManager().getModule(ScoreboardModule.class);

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        FastBoard board = scoreboardModule.boards.remove(player);

        if (board != null) {
            board.delete();
        }
    }

}
