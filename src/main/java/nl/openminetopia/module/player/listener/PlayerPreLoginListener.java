package nl.openminetopia.module.player.listener;

import nl.openminetopia.OpenMinetopia;
import nl.openminetopia.module.data.database.StormDatabase;
import nl.openminetopia.module.data.database.models.PlayerModel;
import nl.openminetopia.module.player.manager.PlayerManager;
import nl.openminetopia.utils.MessageUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.concurrent.CompletableFuture;

public class PlayerPreLoginListener implements Listener {

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        PlayerManager.getPlayerModels().remove(event.getUniqueId());

        try {
            CompletableFuture<PlayerModel> future = StormDatabase.getInstance().loadPlayerModel(event.getUniqueId());
            future.get();
        } catch (Exception e) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, MessageUtils.format("<red>Er is een fout opgetreden bij het laden van je gegevens! Probeer het later opnieuw."));
            OpenMinetopia.getInstance().getLogger().warning("Error loading player model: " + e.getMessage());
        }
    }
}
