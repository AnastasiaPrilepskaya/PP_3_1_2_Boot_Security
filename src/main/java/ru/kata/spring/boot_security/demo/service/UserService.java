package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age, String username, String password);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();

    void updateUser(User user);

    User getUserById(long id);

    User getUserByUsername(String username);
}
