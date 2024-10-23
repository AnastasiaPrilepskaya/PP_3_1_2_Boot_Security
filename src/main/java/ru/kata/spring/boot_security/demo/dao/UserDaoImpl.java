package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createUsersTable() {
        entityManager.createNativeQuery("CREATE TABLE IF NOT EXISTS users (id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                " name VARCHAR(30), last_name VARCHAR(30), age TINYINT, username VARCHAR(30)," +
                " password varchar(30))").executeUpdate();
    }

    @Override
    public void dropUsersTable() {
        entityManager.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
    }

    @Override
    public void saveUser(String name, String lastName, byte age, String username, String password, Set<Role> roles) {
        User user = new User(name, lastName, age, username, password, roles);
        entityManager.persist(user);
    }

    @Override
    public void removeUserById(long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            for (Role role : user.getRoles()) {
                role.getUsers().remove(user);
            }
            user.getRoles().clear();

            entityManager.remove(user);
        }
    }

    @Override
    public List<User> getAllUsers() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Override
    public void cleanUsersTable() {
        entityManager.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
    }

    @Override
    public void updateUserById(long id, String name, String lastName, byte age, String username, String password, Set<Role> roles) {
        User user = entityManager.find(User.class, id);
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(roles);
        entityManager.persist(user);
    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUserByUsername(String username) {
        String query1 = "SELECT u FROM User u LEFT JOIN FETCH u.roles where u.username = :username";
        List<User> query = entityManager.createQuery(query1, User.class).setParameter("username", username).getResultList();
        if (query.isEmpty()) {
            System.out.println("----------------------------------------------");
            return null;
        }
        User user = query.get(0);
        return user;
    }
}

