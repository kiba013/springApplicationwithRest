package spring.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import spring.application.model.User;
import spring.application.service.UserDetService;
import spring.application.service.UserService;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserService userService;


    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String mainPage(Principal principal, Model model) {
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("currentUser", user);

        return "/home";
    }

    @GetMapping("/registration")
    public String getRegistrationForm(Model model) {
        model.addAttribute("userDetail", new User());
        return "/registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userDetail") User user) throws Exception {
        userService.saveUser(user);
        return "redirect:/home";
    }



}
