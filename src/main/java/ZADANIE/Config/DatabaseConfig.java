package ZADANIE.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    public static class TABLE {
        public static final String TASKS = "tasks";
    }

    private static final String DB_FILE = "tasks.db";
    private static final String URL = "jdbc:sqlite:%s".formatted(DB_FILE);

    public DatabaseConfig() throws Exception {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            System.out.println("Could not load the SQLite driver.");
            throw e;
        }

        createTables();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private void createTables() throws Exception {
        var sql = """
            CREATE TABLE IF NOT EXISTS %s(
                Id INTEGER PRIMARY KEY AUTOINCREMENT,
                Content VARCHAR(120) NOT NULL,
                Status VARCHAR(12) NOT NULL
            );
        """.formatted(TABLE.TASKS);

        try (var conn = getConnection(); var stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            System.out.println("Could not create the tables.");
            throw e;
        }
    }
}
