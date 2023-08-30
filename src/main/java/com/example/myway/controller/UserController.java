package com.example.myway.controller;

import com.example.myway.user.User;
import com.example.myway.user.UserRequestDTO;
import com.example.myway.user.UserResponseDTO;
import com.example.myway.user.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public void createUser(@RequestBody UserRequestDTO data){
        User userData = new User(data);
        repository.save(userData);
        return;
    }

    @GetMapping
    public List<UserResponseDTO> getAll(){
        return repository.findAll().stream().map(UserResponseDTO::new).toList();
    }
}
