package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("users", allUsers);
        return "all-users";
    }

    @GetMapping("/user-create")
    public String getUserForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "create-user";
    }

    @PostMapping("/user-create")
    public String addNewUser(@ModelAttribute("user") User user,
                             @RequestParam("selectedRole") String selectedRole,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "user-update";
        user.getRoleSet().add(roleService.getRole(selectedRole));
        userService.saveUser(user);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/user-update/{id}")
    public String updateUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "user-update";
    }

    @PostMapping("/user-update")
    public String updateUser(@ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             @RequestParam("selectedRole") String selectedRole) {
        if (bindingResult.hasErrors())
            return "user-update";
        user.getRoleSet().add(roleService.getRole(selectedRole));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/user-delete/{id}")
    public String deleteUserForm(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String getUserPage(Model model, Principal principal) {
       model.addAttribute("userPage", userService.findByUsername(principal.getName()));
        return "user";
    }
}
