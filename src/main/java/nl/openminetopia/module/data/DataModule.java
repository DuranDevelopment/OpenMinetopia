package nl.openminetopia.module.data;

import nl.openminetopia.OpenMinetopia;
import nl.openminetopia.module.Module;
import nl.openminetopia.module.data.database.DatabaseType;
import nl.openminetopia.module.data.database.StormDatabase;

public class DataModule extends Module {

    @Override
    public void enable() {
        try {

            DatabaseType type = DatabaseType.SQLITE;

            switch (type) {
                case MYSQL:
                    StormDatabase.getInstance().initMySQL();
                default:
                    StormDatabase.getInstance().initSqlite();
            }

        } catch (Exception e) {
            OpenMinetopia.getInstance().getLogger().severe("Error initializing database: " + e.getMessage());
        }
    }

    @Override
    public void disable() {

    }
}
