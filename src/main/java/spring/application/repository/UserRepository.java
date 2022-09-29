package spring.application.repository;

import org.springframework.data.repository.CrudRepository;
import spring.application.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
