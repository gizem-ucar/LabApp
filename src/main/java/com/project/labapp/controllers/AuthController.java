package com.project.labapp.controllers;

import com.project.labapp.entities.Role;
import com.project.labapp.entities.User;
import com.project.labapp.requests.UserLoginRequest;
import com.project.labapp.requests.UserRegisterRequest;
import com.project.labapp.responses.AuthResponse;
import com.project.labapp.security.JwtTokenProvider;
import com.project.labapp.services.RoleService;
import com.project.labapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    private UserService userService;
    private RoleService roleService;

    private PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder, RoleService roleService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserLoginRequest loginRequest){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
        User user = userService.getOneUserByUserName(loginRequest.getUserName());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Bearer " + jwtToken);
        authResponse.setSuccess(true);
        authResponse.setUserId(user.getId());
        authResponse.setRoleId(user.getRole().getRoleId());
        authResponse.setRoleName(user.getRole().getRoleName());
        return authResponse;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequest registerRequest){
        if (userService.getOneUserByUserName(registerRequest.getUserName()) != null)
            return new ResponseEntity<>("Username already in use.", HttpStatus.BAD_REQUEST);
        Role role = roleService.getOneRoleByRoleId(registerRequest.getRoleId());
        if (role == null)
            return null;
        User user = new User();
        user.setUserName(registerRequest.getUserName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUserFirstName(registerRequest.getUserFirstName());
        user.setUserLastName(registerRequest.getUserLastName());
        user.setUserTC(registerRequest.getUserTC());
        user.setRole(role);
        user.setEmail(registerRequest.getEmail());
        userService.saveOneUser(user);
        return new ResponseEntity<>("User successfully registered.", HttpStatus.CREATED);
    }
}
