package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;

//    private final RoleDao roleDao;

    @Autowired
    public DataInitializer(UserService userService) {
        this.userService = userService;
//        this.roleDao = roleDao;
    }

    @PostConstruct
    public void setup() {
        if (!userService.existsByName("ROLE_ADMIN")) {
            userService.save(new Role("ROLE_ADMIN"));
        }
        if (!userService.existsByName("ROLE_USER")) {
            userService.save(new Role("ROLE_USER"));
        }
    }

    @Override
    public void run(String... args) throws Exception {

        if (userService.getUserByUsername("admin") == null) {
            Set<Role> roles = Stream.of(new Role("ROLE_ADMIN")).collect(Collectors.toSet());

            userService.saveUser("admin", "admin", (byte) 12, "admin", "admin", roles);
        }

        if (userService.getUserByUsername("user") == null) {
            Set<Role> roles = Stream.of(new Role("ROLE_USER")).collect(Collectors.toSet());
            userService.saveUser("user", "user", (byte) 11, "user", "user", roles);
        }
    }
}
