package spring.application.service;


import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import spring.application.model.Role;
import spring.application.model.User;
import spring.application.repository.RoleRepository;
import spring.application.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImp implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RoleRepository roleRepository;

    public UserServiceImp(UserRepository userRepository, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
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
        User username = userRepository.findByEmail(user.getEmail());
        if (username == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "User E-mail already exists!");
    }

    @Override
    public User updateUser(User user) {
        User curUser = getUserById(user.getId());
        boolean b = user.getPassword().equals(curUser.getPassword());
        if (!b) {
            curUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        curUser.setUsername(user.getUsername());
        curUser.setAge(user.getAge());
        curUser.setEmail(user.getEmail());
        curUser.setRole(user.getRole());
        return userRepository.save(curUser);

    }

    @Override
    public Long deleteUser(Long id) {
       userRepository.deleteById(id);
        return id;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                mapRolesToAuthorities(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }
}
