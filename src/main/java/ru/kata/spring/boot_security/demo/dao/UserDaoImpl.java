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

    @Transactional
    @Override
    public void saveUser(String name, String lastName, byte age, String username, String password) {
        User user = new User(name, lastName, age, username, password);
        entityManager.persist(user);
    }

    @Transactional
    @Override
    public void removeUserById(long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public List<User> getAllUsers() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public void cleanUsersTable() {
        entityManager.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
    }

    @Transactional
    @Override
    public void updateUserById(long id, String name, String lastName, byte age) {
        User user = entityManager.find(User.class, id);
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        entityManager.persist(user);
    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }


    @Override
    @Transactional
    public User getUserByUsername(String username) {
        String query1 = "SELECT u FROM User u where username = :username";
        List<User> query = entityManager.createQuery(query1, User.class).setParameter("username", username).getResultList();
        if (query.isEmpty()) {
            System.out.println("----------------------------------------------");
            return null;
        }
        User user = query.get(0);
        return user;
    }
}

