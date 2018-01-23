package ua.kpi.apeps.repository.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class ConnectionManager {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream is = ConnectionManager.class.getClassLoader()
                .getResourceAsStream("database.properties")) {
            PROPS.load(is);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager
                .getConnection(PROPS.getProperty("url"), PROPS);
        connection.setAutoCommit(false);
        return connection;
    }
}
