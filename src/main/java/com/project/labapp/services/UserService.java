package com.project.labapp.services;

import com.project.labapp.entities.Role;
import com.project.labapp.entities.User;
import com.project.labapp.repos.UserRepository;
import com.project.labapp.requests.UserRegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;

    private PasswordEncoder passwordEncoder;
    private RoleService roleService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveOneUser(User newUser) {
        return userRepository.save(newUser);
    }

    public User getOneUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateOneUser(Long userId, UserRegisterRequest newUser) {
        Optional<User> user = userRepository.findById(userId);

        Role role = roleService.getOneRoleById(newUser.getRoleId());
        if (role == null)
            return null;

        if (user.isPresent()){
            User foundUser = user.get();
            foundUser.setUserName(newUser.getUserName());
            foundUser.setUserTC(newUser.getUserTC());
            foundUser.setEmail(newUser.getEmail());
            foundUser.setUserFirstName(newUser.getUserFirstName());
            foundUser.setUserLastName(newUser.getUserLastName());
            foundUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            foundUser.setRole(role);
            userRepository.save(foundUser);
            return foundUser;
        }else
            return null;
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    public User getOneUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
