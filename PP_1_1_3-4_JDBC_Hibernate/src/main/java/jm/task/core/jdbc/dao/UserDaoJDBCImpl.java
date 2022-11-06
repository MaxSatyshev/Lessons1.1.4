package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Util util = new Util();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate("create table users(id int NOT NULL AUTO_INCREMENT," +
                    "name varchar(45) NOT NULL," +
                    "lastname varchar(45) NOT NULL," +
                    "age int NOT NULL," +
                    "primary key (id))");
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы юзеров");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate("drop table users");
        } catch (SQLException e) {}
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = util.getConnection().prepareStatement("INSERT INTO users(`name`, lastname, age) VALUES(?, ?, ?);")) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Ошибка добавления нового юзера");
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement ps = util.getConnection().prepareStatement("delete from users where id = ?;")) {
            ps.setInt(1, (int)id);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Ошибка удаления объекта User");
        }

    }

    public List<User> getAllUsers() {
        List<User> listUsers = new ArrayList<>();
        try (Statement statement = util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from users;");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                listUsers.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения списка Users");
        }
        return listUsers;
    }

    public void cleanUsersTable() {
        try (Statement statement = util.getConnection().createStatement()){
            statement.execute("delete from users;");
        } catch (SQLException e) {
            System.out.println("Ошибка очистки таблицы юзеров");
        }
    }
}
