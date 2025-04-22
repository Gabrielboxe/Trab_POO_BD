package com.trabalhopratico.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URI = "jdbc:postgresql://localhost:5432/trabalhopratico";
    private static final String USER = "gabrielo";
    private static final String PASSWORD = "gabrielo";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URI, USER, PASSWORD);
    }
}
