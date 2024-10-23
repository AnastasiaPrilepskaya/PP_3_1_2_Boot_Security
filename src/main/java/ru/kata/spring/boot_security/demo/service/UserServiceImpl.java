package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao userDao;

    private final RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public void createUsersTable() {
        userDao.createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    @Transactional
    @Override
    public void saveUser(String name, String lastName, byte age, String username, String password, Set<Role> roles) {
        var rolesDb = getRolesByRole(roles);
        userDao.saveUser(name, lastName, age, username, password, rolesDb);
    }

    @Transactional
    @Override
    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    @Override
    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        var rolesDb = getRolesByRole(user.getRoles());
        userDao.updateUserById(user.getId(), user.getName(), user.getLastName(), user.getAge(), user.getUsername(), user.getPassword(), rolesDb);
    }

    @Transactional
    @Override
    public User getUserById(long id) {
        var user = userDao.getUserById(id);
        return user;
    }

    @Transactional
    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return userDao.getUserByUsername(username);
    }

    public Set<Role> getRolesByRole(Set<Role> roles) {
        return roleDao.getRolesByRole(roles);
    }

    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    public Set<Role> findByName(String name) {
        return roleDao.findByName(name);
    }

    public boolean existsByName(String roleName) {
        return roleDao.existsByName(roleName);
    }

    @Transactional
    public void save(Role role) {
        roleDao.save(role);
    }

}
