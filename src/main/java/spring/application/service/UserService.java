package spring.application.service;

import spring.application.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUser();

    User getUserById(Long id);

    User getUserByName(String username);

    User saveUser(User user) throws Exception;

    User updateUser(User user);

    Long deleteUser(Long id);

    User findByEmail(String email);
}
