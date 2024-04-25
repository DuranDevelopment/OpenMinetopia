package nl.openminetopia.module.data;

import nl.openminetopia.OpenMinetopia;
import nl.openminetopia.module.Module;
import nl.openminetopia.module.data.database.StormDatabase;

public class DataModule extends Module {

    @Override
    public void enable() {
        try {
            StormDatabase.getInstance().init();
        } catch (Exception e) {
            OpenMinetopia.getInstance().getLogger().severe("Error initializing database: " + e.getMessage());
        }
    }

    @Override
    public void disable() {

    }
}
