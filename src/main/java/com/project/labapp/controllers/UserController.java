package com.project.labapp.controllers;

import com.project.labapp.entities.User;
import com.project.labapp.repos.UserRepository;
import com.project.labapp.requests.UserRegisterRequest;
import com.project.labapp.requests.UserUpdateRequest;
import com.project.labapp.services.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User newUser){
        return userService.saveOneUser(newUser);
    }

    @GetMapping("/{userId}")
    public User getOneUser(@PathVariable Long userId){
        //custom exception
        return userService.getOneUserById(userId);
    }

    @PutMapping("/{userId}")
    public User updateOneUser(@PathVariable Long userId, @RequestParam("file") MultipartFile userImageFile, @ModelAttribute UserUpdateRequest updateUser) throws IOException {
        updateUser.setUserImageFile(userImageFile);
        return userService.updateOneUser(userId, updateUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteOneUser(@PathVariable Long userId) {
        userService.deleteById(userId);
    }
}
