package nl.openminetopia.module.player;

import nl.openminetopia.module.Module;
import nl.openminetopia.module.player.listener.PlayerJoinListener;
import nl.openminetopia.module.player.listener.PlayerPreLoginListener;
import nl.openminetopia.module.player.listener.PlayerQuitListener;

public class PlayerModule extends Module {
    @Override
    public void enable() {
        registerListener(new PlayerPreLoginListener());
        registerListener(new PlayerJoinListener());
        registerListener(new PlayerQuitListener());
    }

    @Override
    public void disable() {

    }
}
