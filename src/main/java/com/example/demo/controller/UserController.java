package com.example.demo.controller;

import com.example.demo.UserNotFoundException;
import com.example.demo.dto.DeletedUserDto;
import com.example.demo.models.User;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        return userServiceImpl.addUser(user);
    }
    //get all
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getAll")
    @ResponseBody
    public Iterable<User> getAllUsers() {
        return userServiceImpl.findAllUsers();
    }

    @GetMapping("/getById")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    public User getById(@RequestParam (value = "userId") long userId) {
        return userServiceImpl.findById(userId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping(path="/delete") // Map ONLY DELETE Requests
    public @ResponseBody String deleteUser (@RequestBody DeletedUserDto deletedUser) {
        if(userServiceImpl.existsById(deletedUser.getId())){
            userServiceImpl.deleteById(deletedUser.getId());
            return "Deleted";
        } else {
            return "User not found";
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getByName")
    public List<User> getByName(@RequestParam (value = "name") String name ) {
        return userServiceImpl.findByName(name);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getByGender")
    public List<User> getByGender(@RequestParam (value = "gender") String gender ) {
        return userServiceImpl.findBySex(gender);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getByAge")
    public List<User> getByAge(@RequestParam (value = "age") int age ) {
        return userServiceImpl.findByAge(age);
    }



}
