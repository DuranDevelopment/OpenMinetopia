package nl.openminetopia.module.data.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DatabaseType {

    SQLITE("SQLite", "org.sqlite.JDBC"),
    MYSQL("MySQL", "com.mysql.cj.jdbc.MysqlDataSource"),
    MARIADB("MariaDB", "org.mariadb.jdbc.Driver");

    private final String displayName, driver;

}
