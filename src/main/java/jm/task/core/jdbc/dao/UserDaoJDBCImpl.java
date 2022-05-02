package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    private Statement statement;
    private ResultSet resultSet;
    private Connection connection;


    public void createUsersTable() {
        String createTableSQL1 = "CREATE TABLE IF NOT EXISTS  user("
                + "id   serial   not null, "
                + "name VARCHAR(255) NOT NULL, "
                + "last_name VARCHAR(255) NOT NULL, "
                + "age INTEGER NOT NULL " + ")";

        try {
            connection = Util.getCon();
            statement = connection.createStatement();
            statement.execute(createTableSQL1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        String deleteTableSQL1 = "DROP TABLE IF EXISTS user";
        try {
            connection = Util.getCon();
            statement = connection.createStatement();
            statement.execute(deleteTableSQL1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement preparedStatement = null;
        try {
            connection = Util.getCon();
            preparedStatement =
                    connection.prepareStatement("INSERT INTO user VALUES(default,?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void removeUserById(long id) {
        PreparedStatement preparedStatement = null;

        try {
            String sql = "DELETE FROM user WHERE id= ?";
            connection = Util.getCon();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try {
            connection = Util.getCon();
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
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return users;
    }

    public void cleanUsersTable() {
        String sql1 = "TRUNCATE TABLE user";
        try {
            connection = Util.getCon();
            statement = connection.createStatement();
            statement.execute(sql1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}
