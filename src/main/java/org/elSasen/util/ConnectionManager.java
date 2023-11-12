package org.elSasen.util;

import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@UtilityClass
public final class ConnectionManager {
    public static final String URL_KEY = "db.url";
    public static final String USERNAME_KEY = "db.username";
    public static final String PASSWORD_KEY = "db.password";
    public static final String DRIVER_KEY = "db.driver";

    static {
        loadDriver();
    }

    private static void loadDriver() {
        try {
            Class.forName(PropertiesUtil.get(DRIVER_KEY));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection get() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
