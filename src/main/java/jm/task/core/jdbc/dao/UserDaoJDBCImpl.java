package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    /*  Создание таблицы для User(ов) – не должно приводить к исключению, если такая таблица уже существует
        Удаление таблицы User(ов) – не должно приводить к исключению, если таблицы не существует
        Очистка содержания таблицы
        Добавление User в таблицу
        Удаление User из таблицы ( по id )
        Получение всех User(ов) из таблицы

     */
    public UserDaoJDBCImpl() {
    }
    private Statement statement;
    private ResultSet resultSet;
    private Connection connection;


    public void createUsersTable() {
        String createTableSQL = "DROP TABLE IF EXISTS user";
      String createTableSQL1 = "CREATE TABLE user("
                + "id   serial   not null, "
                + "name VARCHAR(255) NOT NULL, "
                + "last_name VARCHAR(255) NOT NULL, "
               + "age INTEGER NOT NULL " + ")";
        connection = Util.getCon();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
             statement.execute(createTableSQL);
             statement.execute(createTableSQL1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void dropUsersTable() {
        String deleteTableSQL1 = "DROP TABLE IF EXISTS user";
        //String deleteTableSQL = "DROP TABLE user";
        connection = Util.getCon();

        try {
            statement = connection.createStatement();
            statement.execute(deleteTableSQL1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public void saveUser(String name, String lastName, byte age) {
        //User user = new User(name,lastName,age);
        connection = Util.getCon();

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO user VALUES(default,?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void removeUserById(long id) {
        connection = Util.getCon();
        // = null;
        try {

            String sql = "DELETE FROM user WHERE id= ?";
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        connection = Util.getCon();
        try {
            statement = connection.createStatement();
            String getAllUsers = "SELECT * FROM user";
            resultSet = statement.executeQuery(getAllUsers);
            while (resultSet.next()) {

                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return users;
    }

    public void cleanUsersTable() {
        connection = Util.getCon();
        String sql1 = "TRUNCATE TABLE user";
        try {
            statement = connection.createStatement();
            statement.execute(sql1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
