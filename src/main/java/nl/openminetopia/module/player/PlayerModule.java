package nl.openminetopia.module.player;

import nl.openminetopia.module.Module;
import nl.openminetopia.module.player.listener.PlayerJoinListener;

public class PlayerModule extends Module {
    @Override
    public void enable() {
        registerListener(new PlayerJoinListener());
    }

    @Override
    public void disable() {

    }
}
