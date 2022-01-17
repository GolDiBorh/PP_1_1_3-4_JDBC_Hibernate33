package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/temp";
    private static final String USER = "root";
    private static final String PASSWORD = "Gogobizare32";

    private static Connection con;


    public static Connection getCon() {
        try {
            con = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }
    // реализуйте настройку соеденения с БД
}
