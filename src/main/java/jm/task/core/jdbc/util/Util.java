package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/temp?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "Gogobizare32";
    private static Connection con;

    public static Connection getCon() {
        try {
            con = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
