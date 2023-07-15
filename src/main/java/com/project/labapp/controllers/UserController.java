package com.project.labapp.controllers;

import com.project.labapp.entities.User;
import com.project.labapp.repos.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }

    @GetMapping("/{userId}")
    public User getOneUser(@PathVariable Long userId){
        //custom exception
        return userRepository.findById(userId).orElse(null);
    }

    @PutMapping("/{userId}")
    public User updateOneUser(@PathVariable Long userId, @RequestBody User newUser){
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

    @DeleteMapping("/{userId}")
    public void deleteOneUser(@PathVariable Long userId) {
        userRepository.deleteById(userId);
    }
}
