package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {

    List<Role> getAllRoles();

    Set<Role> getRolesByRole(Set<Role> roles);

    Set<Role> findByName(String name);

    boolean existsByName(String name);

    void save(Role role);

}
