package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;


    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Principal currentUser, Model model) {
        List<Role> roles = new ArrayList<>(userService.getAllRoles());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("currentUser", userService.getUserByUsername(currentUser.getName()));
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roles);
        return "index";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("user") User person) {
        return "index";
    }

    @PostMapping()
    public String create(@ModelAttribute("newUser") User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "index";

        userService.saveUser(user.getName(), user.getLastName(), user.getAge(), user.getUsername(), user.getPassword(), user.getRoles());
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "index";

        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

}
