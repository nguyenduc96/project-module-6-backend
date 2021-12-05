package com.diosa.controller;

import com.diosa.model.user.*;
import com.diosa.service.JwtService;
import com.diosa.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/set-avatar")
    public ResponseEntity<User> setAvatar(@RequestBody String avatar, Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = userService.findByUsername(userPrinciple.getUsername()).get();
        avatar = avatar.replace("\"","");
        user.setAvatar(avatar);
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/get-user")
    public ResponseEntity<User> getUser(Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = userService.findByUsername(userPrinciple.getUsername()).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/set-password")
    public ResponseEntity<?> setPassword(@RequestBody User user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        if (userRequest.getAvatar() == null) {
            user.setAvatar("https://www.donkey.bike/wp-content/uploads/2020/12/user-member-avatar-face-profile-icon-vector-22965342-e1608640557889.jpg");
        }
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setId(2L);
        roles.add(role);
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateTokenLogin(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User currentUser = userService.findByUsername(userPrinciple.getUsername()).get();
        JwtResponse jwtResponse = new JwtResponse(
                token,
                currentUser.getId(),
                currentUser.getUsername(),
                userPrinciple.getAuthorities());
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }
}
