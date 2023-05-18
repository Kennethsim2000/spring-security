package com.example.demo.controller;

import com.example.demo.UserNotFoundException;
import com.example.demo.config.CommonResult;
import com.example.demo.dto.DeletedUserDto;
import com.example.demo.dto.FilterDto;
import com.example.demo.dto.NewUserDto;
import com.example.demo.dto.UserDto;
import com.example.demo.models.User;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.vo.UserVo;
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
    public User addUser(@RequestBody NewUserDto user) {
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
            userServiceImpl.deleteUser(deletedUser.getId());
            return "Deleted";
        } else {
            return "User not found";
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/getByName")
    public List<User> getByName(@RequestBody FilterDto filter ) {
        return userServiceImpl.findByName(filter.getName());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/getByGender")
    public List<User> getByGender(@RequestBody FilterDto filter ) {
        return userServiceImpl.findBySex(filter.getGender());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/getByAge")
    public List<User> getByAge(@RequestParam (value = "age") int age ) {
        return userServiceImpl.findByAge(age);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/update")
    public CommonResult<UserVo> updateUser(@RequestBody UserDto user) {
        User updatedUser = userServiceImpl.updateUser(user);
        UserVo userResponse = UserVo.builder()
                .id(updatedUser.getId())
                .name(updatedUser.getName())
                .age(updatedUser.getAge())
                .createTime(updatedUser.getCreateTime())
                .administrator(updatedUser.getAdministrator())
                .build();
        return CommonResult.success(userResponse, "User updated successfully");
    }


}
