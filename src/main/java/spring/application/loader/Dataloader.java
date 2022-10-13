package spring.application.loader;

import org.springframework.stereotype.Component;
import spring.application.model.Role;
import spring.application.model.User;
import spring.application.repository.RoleRepository;
import spring.application.service.UserService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collections;

@Component
public class Dataloader {

    private final UserService userService;

    private final RoleRepository roleRepository;

    public Dataloader(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }


    @PostConstruct
    public void loadData() throws Exception {
        userService.saveUser(
                new User("admin", "admin",
                        "admin@admin.com", 1, Collections.singleton(roleRepository.save(new Role("ROLE_ADMIN")))));
        userService.saveUser(
                new User("user", "user",
                        "user@user.com", 1, Collections.singleton(roleRepository.save(new Role("ROLE_USER")))));
    }
    @PreDestroy
    public void removeData() {
        roleRepository.deleteAll();
        userService.deleteUser(userService.findByEmail("admin@admin.com").getId());
        userService.deleteUser(userService.findByEmail("user@user.com").getId());
    }

}
