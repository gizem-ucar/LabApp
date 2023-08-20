package com.project.labapp.services;

import com.project.labapp.entities.Role;
import com.project.labapp.entities.User;
import com.project.labapp.repos.UserRepository;
import com.project.labapp.requests.UserUpdateRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public User updateOneUser(Long userId, UserUpdateRequest updateUser) throws IOException {

        byte[] imageBytes = updateUser.getUserImageFile().getBytes();

        Optional<User> user = userRepository.findById(userId);

        Role role = roleService.getOneRoleByRoleId(updateUser.getRoleId());


        if (user.isPresent()){
            User foundUser = user.get();
            if (updateUser.getUserName() == null){
                foundUser.setUserName(user.get().getUserName());
            }
            foundUser.setUserName(updateUser.getUserName());
            if (updateUser.getUserTC() == null){
                foundUser.setUserTC(user.get().getUserTC());
            }
            foundUser.setUserTC(updateUser.getUserTC());
            if (updateUser.getEmail() == null){
                foundUser.setEmail(user.get().getEmail());
            }
            foundUser.setEmail(updateUser.getEmail());
            if (updateUser.getUserFirstName() == null){
                foundUser.setUserFirstName(user.get().getUserFirstName());
            }
            foundUser.setUserFirstName(updateUser.getUserFirstName());
            if (updateUser.getUserLastName() == null){
                foundUser.setUserLastName(user.get().getUserLastName());
            }
            foundUser.setUserLastName(updateUser.getUserLastName());
            if (updateUser.getUserImageFile() == null){
                foundUser.setUserImage(user.get().getUserImage());
            }
            foundUser.setUserImage(imageBytes);
            if (updateUser.getPassword() == null){
                foundUser.setPassword(user.get().getPassword());
            }
            foundUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
            if (role == null)
                foundUser.setRole(user.get().getRole());
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
