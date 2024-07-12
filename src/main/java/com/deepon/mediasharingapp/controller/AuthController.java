package com.deepon.mediasharingapp.controller;

import com.deepon.mediasharingapp.exception.UserException;
import com.deepon.mediasharingapp.model.User;
import com.deepon.mediasharingapp.repository.UserRepository;
import com.deepon.mediasharingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<User> registerUserHandler(@RequestBody User user) throws UserException {
        User createdUser = userService.registerUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    @GetMapping("/signin")
    public ResponseEntity<User> signinHandler(Authentication auth) throws BadCredentialsException {
       Optional<User> optional = userRepository.findByEmail(auth.getName());

       if(optional.isPresent()) {
           return new ResponseEntity<>(optional.get(), HttpStatus.ACCEPTED);
       }
       throw new BadCredentialsException("Invalid Username or Password");
    }
}
