package com.diosa.controller;

import com.diosa.model.user.JwtResponse;
import com.diosa.model.user.User;
import com.diosa.model.user.UserPrinciple;
import com.diosa.model.user.UserRequest;
import com.diosa.service.JwtService;
import com.diosa.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.Date;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {
    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<User> register(UserRequest userRequest) throws IOException {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        MultipartFile file = userRequest.getAvatar();
        long times = new Date().getTime();
        String avatar = times + file.getOriginalFilename();
        FileCopyUtils.copy(file.getBytes(), new File(fileUpload + avatar));
        user.setAvatar(avatar);
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
