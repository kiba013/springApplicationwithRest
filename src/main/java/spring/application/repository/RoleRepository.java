package spring.application.repository;

import org.springframework.data.repository.CrudRepository;
import spring.application.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
