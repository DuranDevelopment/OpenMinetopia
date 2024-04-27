package nl.openminetopia.api.player;

import lombok.Getter;
import lombok.Setter;
import nl.openminetopia.module.data.database.models.PlayerModel;

import java.util.UUID;

@Getter
@Setter
public class MinetopiaPlayer {

    private final UUID uuid;
    private final PlayerModel playerModel;

    public MinetopiaPlayer(UUID uuid, PlayerModel playerModel) {
        this.uuid = uuid;
        this.playerModel = playerModel;
    }
}
