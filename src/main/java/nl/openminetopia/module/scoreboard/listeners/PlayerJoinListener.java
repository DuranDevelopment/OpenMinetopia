package nl.openminetopia.module.scoreboard.listeners;

import fr.mrmicky.fastboard.FastBoard;
import nl.openminetopia.OpenMinetopia;
import nl.openminetopia.module.scoreboard.ScoreboardModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private ScoreboardModule scoreboardModule = OpenMinetopia.getModuleManager().getModule(ScoreboardModule.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        FastBoard board = new FastBoard(player);

        board.updateTitle("Sven zijn vader");

        scoreboardModule.boards.put(player, board);
    }

}
