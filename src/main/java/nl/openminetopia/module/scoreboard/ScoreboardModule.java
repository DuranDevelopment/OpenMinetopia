package nl.openminetopia.module.scoreboard;

import com.mysql.cj.x.protobuf.MysqlxExpect;
import fr.mrmicky.fastboard.FastBoard;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import nl.openminetopia.OpenMinetopia;
import nl.openminetopia.module.Module;
import nl.openminetopia.module.scoreboard.listeners.PlayerJoinListener;
import nl.openminetopia.module.scoreboard.listeners.PlayerQuitListener;
import nl.openminetopia.utils.MessageUtils;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardModule extends Module {

    public final Map<Player, FastBoard> boards = new HashMap<>();

    @Override
    public void enable() {
        OpenMinetopia.getInstance().getServer().getScheduler().runTaskTimer(OpenMinetopia.getInstance(), () -> {
            for (FastBoard board : this.boards.values()) {
                updateBoard(board);
            }
        }, 0, 20);

        registerListener(new PlayerQuitListener());
        registerListener(new PlayerJoinListener());
    }

    @Override
    public void disable() {

    }

    private void updateBoard(FastBoard board) {
        board.updateLines(
            serializeLine(MessageUtils.format("<blue>Blauw"))
        );
    }

    public String serializeLine(Component value) {
        return LegacyComponentSerializer.legacySection().serialize(value);
    }

}
