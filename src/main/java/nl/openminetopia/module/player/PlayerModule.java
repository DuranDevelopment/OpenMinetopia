package nl.openminetopia.module.player;

import nl.openminetopia.module.Module;
import nl.openminetopia.module.player.listener.PlayerPreLoginListener;

public class PlayerModule extends Module {
    @Override
    public void enable() {
        registerListener(new PlayerPreLoginListener());
    }

    @Override
    public void disable() {

    }
}
