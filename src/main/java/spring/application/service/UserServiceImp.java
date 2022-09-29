package spring.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import spring.application.model.User;
import spring.application.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImp(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUser() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveUser(User user) throws Exception {
        User username = userRepository.findByUsername(user.getUsername());
        if (username == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Username already exists!");
    }

    @Override
    public User updateUser(Long id, User user) {
        User curUser = getUserById(id);
        curUser.setUsername(user.getUsername());
        curUser.setAge(user.getAge());
        curUser.setEmail(user.getEmail());
        curUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        curUser.setRoleSet(user.getRoleSet());
        return userRepository.save(curUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
