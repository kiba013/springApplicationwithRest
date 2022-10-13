package spring.application.controller.restController;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.application.model.Role;
import spring.application.model.User;
import spring.application.repository.RoleRepository;
import spring.application.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
public class rootRestController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public rootRestController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin/api/users")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping("/admin/api/users")
    public User createNewUser(@RequestBody User user) throws Exception {
        return userService.saveUser(user);
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    @GetMapping("/admin/api/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }


    @DeleteMapping("/admin/api/users/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.deleteUser(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

    @PutMapping("/admin/api/users")
    public ResponseEntity updateUser(@RequestBody User user) {
        try {
            return  ResponseEntity.ok(userService.updateUser(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong!");
        }
    }


    @GetMapping("/api/user")
    public ResponseEntity<User> getUser(Principal principal) {
        return new ResponseEntity<>(userService.findByEmail(principal.getName()), HttpStatus.OK);
    }
}
