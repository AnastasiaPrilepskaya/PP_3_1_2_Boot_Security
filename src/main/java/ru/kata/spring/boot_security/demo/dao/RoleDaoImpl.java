package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("from Role", Role.class).getResultList();
    }

    @Override
    public Set<Role> getRolesByRole(Set<Role> roles) {
        List<String> roleNames = roles.stream()
                .map(Role::getRole)
                .collect(Collectors.toList());
        List<Role> foundRoles = entityManager.createQuery(
                        "SELECT r FROM Role r WHERE r.role IN :roleList", Role.class)
                .setParameter("roleList", roleNames)
                .getResultList();

        return new HashSet<>(foundRoles);

    }

}
