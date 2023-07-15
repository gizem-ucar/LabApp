package com.project.labapp.services;

import com.project.labapp.entities.User;
import com.project.labapp.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveOneUser(User newUser) {
        return userRepository.save(newUser);
    }

    public User getOneUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateOneUser(Long userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            User foundUser = user.get();
            foundUser.setUserName(newUser.getUserName());
            foundUser.setUserTC(newUser.getUserTC());
            foundUser.setEmail(newUser.getEmail());
            foundUser.setUserImage(newUser.getUserImage());
            foundUser.setUserFirstName(newUser.getUserFirstName());
            foundUser.setUserLastName(newUser.getUserLastName());
            foundUser.setPassword(newUser.getPassword());
            foundUser.setRoleId(newUser.getRoleId());
            userRepository.save(foundUser);
            return foundUser;
        }else
            return null;
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
}
