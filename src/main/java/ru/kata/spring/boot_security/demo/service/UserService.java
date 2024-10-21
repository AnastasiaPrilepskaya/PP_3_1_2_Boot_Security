package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age, String username, String password, Set<Role> role);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();

    void updateUser(User user);

    User getUserById(long id);

    User getUserByUsername(String username);

    Set<Role> getRolesByRole(Set<Role> roles);

    List<Role> getAllRoles();
}
