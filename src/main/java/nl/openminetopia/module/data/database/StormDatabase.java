package nl.openminetopia.module.data.database;

import com.craftmend.storm.Storm;
import com.craftmend.storm.api.StormModel;
import com.craftmend.storm.api.enums.Where;
import com.craftmend.storm.connection.hikaricp.HikariDriver;
import com.craftmend.storm.connection.sqlite.SqliteFileDriver;
import com.zaxxer.hikari.HikariConfig;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import nl.openminetopia.OpenMinetopia;
import nl.openminetopia.module.data.database.models.BankAccountModel;
import nl.openminetopia.module.data.database.models.PlayerModel;
import nl.openminetopia.module.player.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StormDatabase {
    private static StormDatabase instance;
    private @Getter Storm storm;
    private final static @Getter ExecutorService executorService = Executors.newFixedThreadPool(10);

    private StormDatabase() {
        // Private constructor to prevent instantiation
    }

    public static StormDatabase getInstance() {
        if (instance == null) {
            instance = new StormDatabase();
        }
        return instance;
    }

    // TODO: Add configurable database credentials, keeping it static for testing now

    public void initMySQL() throws SQLException {
        String host = "localhost";
        int port = 3306;
        String name = "test";
        String username = "root";
        String password = "test";

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + name);
        config.setUsername(username);
        config.setPassword(password);
        storm = new Storm(new HikariDriver(config));

        storm.registerModel(new PlayerModel());
        storm.runMigrations();

        OpenMinetopia.getInstance().getLogger().info("Successfully connected to the database. (Using: " + DatabaseType.MYSQL.getDisplayName() + ")");
    }

    public void initSqlite() throws SQLException, ClassNotFoundException {
        Class.forName(DatabaseType.SQLITE.getDriver());
        storm = new Storm(new SqliteFileDriver(new File(OpenMinetopia.getInstance().getDataFolder(), "database.db")));

        storm.registerModel(new PlayerModel());
        storm.registerModel(new BankAccountModel());
        storm.runMigrations();

        OpenMinetopia.getInstance().getLogger().info("Successfully connected to the database. (Using: " + DatabaseType.SQLITE.getDisplayName() + ")");
    }

    public CompletableFuture<Optional<PlayerModel>> findPlayerModel(@NotNull UUID uuid) {
        CompletableFuture<Optional<PlayerModel>> completableFuture = new CompletableFuture<>();
        executorService.submit(() -> {
            try {
                Collection<PlayerModel> playerModel;
                playerModel = storm.buildQuery(PlayerModel.class)
                        .where("uuid", Where.EQUAL, uuid.toString())
                        .limit(1)
                        .execute()
                        .join();
                Bukkit.getScheduler().runTaskLaterAsynchronously(OpenMinetopia.getInstance(), () -> completableFuture.complete(playerModel.stream().findFirst()), 1L);
            } catch (Exception exception) {
                exception.printStackTrace();
                completableFuture.completeExceptionally(exception);
            }
        });
        return completableFuture;
    }

    public CompletableFuture<PlayerModel> loadPlayerModel(UUID uuid) {
        CompletableFuture<PlayerModel> completableFuture = new CompletableFuture<>();
        StormDatabase.getInstance().findPlayerModel(uuid).thenAccept(playerModel -> {
            PlayerManager.getPlayerModels().remove(uuid);

            if (playerModel.isEmpty()) {
                PlayerModel createdModel = new PlayerModel();
                createdModel.setUniqueId(uuid);

                PlayerManager.getPlayerModels().put(uuid, createdModel);
                completableFuture.complete(createdModel);

                saveStormModel(createdModel);
                return;
            }

            PlayerManager.getPlayerModels().put(uuid, playerModel.get());
            completableFuture.complete(playerModel.get());
        });

        return completableFuture;
    }

    public CompletableFuture<Integer> saveStormModel(StormModel stormModel) {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        executorService.submit(() -> {
            try {
                completableFuture.complete(storm.save(stormModel));
            } catch (SQLException exception) {
                completableFuture.completeExceptionally(exception);
            }
        });

        return completableFuture;
    }
}