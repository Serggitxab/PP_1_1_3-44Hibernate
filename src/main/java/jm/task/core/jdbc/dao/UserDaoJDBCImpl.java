package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {


    }
    private static Statement statement;
    private static Connection connection = Util.getConnection();

    public void createUsersTable() {


        String sql = "Create table users( id  int NOT NULL AUTO_INCREMENT primary key, name varchar(45)," +
                " lastName varchar(45), age int) ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {

        }
    }

    public  void dropUsersTable() {
        String sql = "Drop table users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3,  age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных" );

        } catch (SQLException e) {

        }

    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {

        }


    }

    public List<User> getAllUsers() {

        List<User> listUser = new ArrayList<>();
        String sql = "SELECT id, name,lastName,age FROM users";
        Statement statement = null;
        try {
            statement = Util.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastNAme"));
                user.setAge(resultSet.getByte("age"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listUser;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM USERS ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }
}