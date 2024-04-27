package nl.openminetopia.module.data;

import nl.openminetopia.OpenMinetopia;
import nl.openminetopia.module.Module;
import nl.openminetopia.module.data.database.StormDatabase;
import nl.openminetopia.module.player.listener.PlayerJoinListener;
import nl.openminetopia.module.player.listener.PlayerPreLoginListener;

public class DataModule extends Module {

    @Override
    public void enable() {
        try {
            StormDatabase.getInstance().init();
        } catch (Exception e) {
            OpenMinetopia.getInstance().getLogger().severe("Error initializing database: " + e.getMessage());
        }

        registerListener(new PlayerPreLoginListener());
        registerListener(new PlayerJoinListener());
    }

    @Override
    public void disable() {

    }
}
