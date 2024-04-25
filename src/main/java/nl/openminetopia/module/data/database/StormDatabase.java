package nl.openminetopia.module.data.database;

import com.craftmend.storm.Storm;
import com.craftmend.storm.api.StormModel;
import com.craftmend.storm.connection.hikaricp.HikariDriver;
import com.craftmend.storm.connection.sqlite.SqliteFileDriver;
import com.zaxxer.hikari.HikariConfig;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nl.openminetopia.OpenMinetopia;

import java.io.File;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StormDatabase {
    private static @Getter StormDatabase instance;
    private @Getter Storm storm;
    private final static @Getter ExecutorService executorService = Executors.newFixedThreadPool(10);

    private final String currentDirectory = System.getProperty("user.dir");

    // TODO: Add configurable database credentials, keeping it static for testing now

    public StormDatabase() {
        instance = this;
    }

    public void init() throws SQLException, ClassNotFoundException {

        // TODO: Remove this once config is done
        String type = "mysql";
        String host = "localhost";
        int port = 3306;
        String name = "test";
        String username = "root";
        String password = "test";

        if (type.equalsIgnoreCase("sqlite")) {
            Class.forName("org.sqlite.JDBC");
            storm = new Storm(new SqliteFileDriver(new File(currentDirectory, "database.db")));
            return;
        }

        Class.forName("com.mysql.cj.jdbc.MysqlDataSource");
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + name);
        config.setUsername(username);
        config.setPassword(password);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("jdbcCompliantTruncation", "false");
        config.setPoolName("OpenMinetopiaPool");

        storm = new Storm(new HikariDriver(config));

        storm.runMigrations();

        OpenMinetopia.getInstance().getLogger().info("Successfully connected to the database.");
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