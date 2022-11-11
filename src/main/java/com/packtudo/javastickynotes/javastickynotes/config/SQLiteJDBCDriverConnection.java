package com.packtudo.javastickynotes.javastickynotes.config;

import java.sql.*;

public class SQLiteJDBCDriverConnection {

    public static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:banco.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
