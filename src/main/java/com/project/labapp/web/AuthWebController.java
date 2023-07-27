package com.project.labapp.web;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthWebController {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    private UserService userService;
    private RoleService roleService;

    private PasswordEncoder passwordEncoder;

    public AuthWebController(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder, RoleService roleService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @RequestMapping(value = "/web/login", method = RequestMethod.GET)
    public String getlogin() {
        return "Auth/login"; // login.html Thymeleaf şablonunu döndürür
    }

    @RequestMapping(value = "/web/login", method = RequestMethod.POST)
    public String  login(@ModelAttribute(name = "loginRequest") UserLoginRequest loginRequest, Model model){
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

        if (authResponse.getSuccess()){
            return "home";
        }
        // Ana sayfaya yönlendirmek için redirectAttributes ile bilgileri gönder
        model.addAttribute("invalidCredentials", true);

        return "Auth/login"; // "redirect:" ile "/web/home" sayfasına yönlendir
    }


    @RequestMapping(value = "/web/register", method = RequestMethod.GET)
    public String getregister() {
        return "Auth/register"; // register.html Thymeleaf şablonunu döndürür
    }

    @RequestMapping(value = "/web/register", method = RequestMethod.POST)
    public String register(@ModelAttribute(name = "registerRequest") UserRegisterRequest registerRequest, Model model){
        if (userService.getOneUserByUserName(registerRequest.getUserName()) != null)
            return "Auth/register";
        Role role = roleService.getOneRoleById(registerRequest.getRoleId());
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

        // Ana sayfaya yönlendirmek için redirectAttributes ile bilgileri gönder
        model.addAttribute("validCredentials", true);

        return "home";
    }
}
