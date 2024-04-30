package nl.openminetopia.module.scoreboard;

import com.mysql.cj.x.protobuf.MysqlxExpect;
import fr.mrmicky.fastboard.FastBoard;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import nl.openminetopia.OpenMinetopia;
import nl.openminetopia.module.Module;
import nl.openminetopia.module.scoreboard.listeners.PlayerJoinListener;
import nl.openminetopia.module.scoreboard.listeners.PlayerQuitListener;
import nl.openminetopia.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

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
        boards.clear();
    }

    private void updateBoard(FastBoard board) {

        TimeZone timeZone = TimeZone.getTimeZone("Europe/Amsterdam");

        // Datum en tijd ophalen
        Date now = new Date();

        // Formatteren van de datum en tijd
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy/HH:mm");
        dateFormat.setTimeZone(timeZone);

        board.updateLines(
                serializeLine(MessageUtils.format("<blue>player#getStad()")),
                "",
                serializeLine(MessageUtils.format("<blue>Datum:")),
                dateFormat.format(now),
                "",
                serializeLine(MessageUtils.format("<blue>Level:")),
                "player#getLevel()",
                "",
                serializeLine(MessageUtils.format("<blue>Fitheid:")),
                "player#getFitheid/250",
                "",
                serializeLine(MessageUtils.format("<blue>Graytokens")),
                "player#getGraytokens",
                "",
                serializeLine(MessageUtils.format("<blue>play.dusdavidgaymes.nl"))

        );
    }

    public String serializeLine(Component value) {
        return LegacyComponentSerializer.legacySection().serialize(value);
    }

}
