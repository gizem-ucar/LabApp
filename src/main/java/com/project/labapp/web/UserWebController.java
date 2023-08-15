package com.project.labapp.web;

import com.project.labapp.entities.User;
import com.project.labapp.requests.UserRegisterRequest;
import com.project.labapp.requests.UserUpdateRequest;
import com.project.labapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class UserWebController {

    private UserService userService;

    public UserWebController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/web/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/web/users")
    public User createUser(@RequestBody User newUser){
        return userService.saveOneUser(newUser);
    }

    @GetMapping("/web/users/{userId}")
    public User getOneUser(@PathVariable Long userId){
        //custom exception
        return userService.getOneUserById(userId);
    }

    @PutMapping("/web/users/{userId}")
    public User updateOneUser(@PathVariable Long userId, @RequestBody UserUpdateRequest newUser) throws IOException {
        return userService.updateOneUser(userId, newUser);
    }

    @DeleteMapping("/web/users/{userId}")
    public void deleteOneUser(@PathVariable Long userId) {
        userService.deleteById(userId);
    }
}
