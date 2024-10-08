package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserDao {

    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age, String username, String password);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();

    void updateUserById(long id, String name, String lastName, byte age);

    User getUserById(long id);

    User getUserByUsername(String username);
}
