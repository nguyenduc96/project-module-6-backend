package com.diosa.controller;

import com.diosa.model.project.Project;
import com.diosa.model.user.*;
import com.diosa.service.JwtService;
import com.diosa.service.project.IProjectService;
import com.diosa.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/check-unique")
    public ResponseEntity<List<UserUnique>> checkUnique() {
        List<UserUnique> userUniques = new ArrayList<>();
        Iterable<User> users = userService.findAll();
        for (User user : users) {
            UserUnique userUnique = new UserUnique();
            userUnique.setUsername(user.getUsername());
            userUnique.setEmail(user.getEmail());
            userUniques.add(userUnique);
        }
        return new ResponseEntity<>(userUniques, HttpStatus.OK);
    }


    @PostMapping("/set-avatar")
    public ResponseEntity<User> setAvatar(@RequestBody String avatar, Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = userService.findByUsername(userPrinciple.getUsername()).get();
        avatar = avatar.replace("\"", "");
        user.setAvatar(avatar);
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/get-user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findById(id).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/user-info")
    public ResponseEntity<User> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = userService.findByUsername(userPrinciple.getUsername()).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/set-password")
    public ResponseEntity<?> setPassword(@RequestBody ChangePassword changePassword, Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = userService.findByUsername(userPrinciple.getUsername()).get();
        if (passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
