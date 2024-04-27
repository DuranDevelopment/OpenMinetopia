package nl.openminetopia.module.player.manager;

import lombok.Getter;
import nl.openminetopia.api.player.MinetopiaPlayer;
import nl.openminetopia.module.data.database.models.PlayerModel;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {
    public static @Getter HashMap<UUID, PlayerModel> playerModels = new HashMap<>();

    public static @Nullable MinetopiaPlayer getMinetopiaPlayer(@NotNull Player player) {
        if (!playerModels.containsKey(player.getUniqueId())) {
            return null;
        }

        return new MinetopiaPlayer(player.getUniqueId(), playerModels.get(player.getUniqueId()));
    }
}