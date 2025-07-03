package com.example.jwtAutenticationDemo.controller;

import com.example.jwtAutenticationDemo.Dto.AuthRequest;
import com.example.jwtAutenticationDemo.entity.UserInfo;
import com.example.jwtAutenticationDemo.jwtconfig.JwtService;
import com.example.jwtAutenticationDemo.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@Log4j2
public class Controller {


    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcomeMessage(){
        return "welcome to Jwt-Spring-Security-Demo";
    }


    @GetMapping("/adminHome")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminHomePage(){
        return "welcome to admin page";
    }

    @PostMapping("/save")
    public String saveUser(@RequestBody UserInfo userInfo){
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setUsername(userInfo.getUsername());
        userInfo1.setRoles(userInfo.getRoles());
        userInfo1.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userRepository.save(userInfo1);
        return "user save";
    }
    @GetMapping("/userHome")
    @PreAuthorize("hasRole('USER')")
    public String userHomePage(){
        return "welcome to user page";
    }

    @GetMapping("/user")
    public String getUserProfile(Principal principal){   //here principle object return login user.
        return principal.getName();
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            log.info("this is  jwt token {}",jwtService.generateToken(authRequest.getUsername()));
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }


    }
}
