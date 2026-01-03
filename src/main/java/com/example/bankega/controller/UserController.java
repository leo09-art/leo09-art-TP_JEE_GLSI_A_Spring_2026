package com.example.bankega.controller;

import com.example.bankega.entity.User;
import com.example.bankega.repository.UserRepository;
import com.example.bankega.entity.User;
import com.example.bankega.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createdUser(@RequestBody User user){
        User userCreated = userRepository.save(user);
        return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
    }
}
