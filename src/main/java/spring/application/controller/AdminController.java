package spring.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.JMoleculesConverters;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.application.model.User;
import spring.application.repository.RoleRepository;
import spring.application.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String getAdminPage(Model model, Principal principal) {
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("users", userService.getAllUser());
        model.addAttribute("currentUser", user);
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("newUser", new User());
        return "admin/admin";
    }

    @PostMapping
    public String createNewUser(@ModelAttribute("newUser") User user) throws Exception {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable("id") Long id,
                             @ModelAttribute("user") User user) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


}
