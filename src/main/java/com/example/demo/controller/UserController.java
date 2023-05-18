package com.example.demo.controller;

import com.example.demo.UserNotFoundException;
import com.example.demo.config.CommonResult;
import com.example.demo.dto.DeletedUserDto;
import com.example.demo.dto.FilterDto;
import com.example.demo.dto.NewUserDto;
import com.example.demo.dto.UserDto;
import com.example.demo.models.User;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.vo.ListUserVo;
import com.example.demo.vo.UserVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @PostMapping("/add")
    public CommonResult<UserVo> addUser(@RequestBody NewUserDto user) {
        User newUser = userServiceImpl.addUser(user);
        UserVo userResponse = UserVo.builder()
                .id(newUser.getId())
                .name(newUser.getName())
                .age(newUser.getAge())
                .createTime(newUser.getCreateTime())
                .administrator(newUser.getAdministrator())
                .build();
        return CommonResult.success(userResponse, "User added");
    }
    //get all
    @GetMapping("/getAll")
    public CommonResult<ListUserVo> getAllUsers() {
        List<User> lst = userServiceImpl.findAllUsers();
        ListUserVo userListResponse = ListUserVo.builder()
                .list(lst)
                .build();
        return CommonResult.success(userListResponse, "users returned");
    }

    @GetMapping("/getById")
    @ResponseBody
    public CommonResult<UserVo> getById(@RequestParam (value = "userId") long userId) {
        User user = userServiceImpl.findById(userId);
        UserVo userResponse = UserVo.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .createTime(user.getCreateTime())
                .administrator(user.getAdministrator())
                .build();
        return CommonResult.success(userResponse, "User found");
    }

    @DeleteMapping(path="/delete") // Map ONLY DELETE Requests
    public CommonResult<UserVo> deleteUser (@RequestBody DeletedUserDto deletedUser) {
        if(userServiceImpl.existsById(deletedUser.getId())){ //if user exists
            User user = userServiceImpl.deleteUser(deletedUser.getId());
            UserVo userResponse = UserVo.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .age(user.getAge())
                    .createTime(user.getCreateTime())
                    .administrator(user.getAdministrator())
                    .build();
            return CommonResult.success(userResponse, "User updated successfully");
        } else {
            return CommonResult.failed(404,"User does not exist");
        }
    }

    @PostMapping("/getByName")
    public CommonResult<ListUserVo> getByName(@RequestBody FilterDto filter ) {
        List<User> lst = userServiceImpl.findByName(filter.getName());
        ListUserVo userListResponse = ListUserVo.builder()
                .list(lst)
                .build();
        return CommonResult.success(userListResponse, "users returned");
    }

    @PostMapping("/getByGender")
    public CommonResult<ListUserVo> getByGender(@RequestBody FilterDto filter ) {
        List<User> lst =  userServiceImpl.findBySex(filter.getGender());
        ListUserVo userListResponse = ListUserVo.builder()
                .list(lst)
                .build();
        return CommonResult.success(userListResponse, "users returned");
    }

    @PostMapping("/getByAge")
    public CommonResult<ListUserVo> getByAge(@RequestParam (value = "age") int age ) {
        List<User> lst = userServiceImpl.findByAge(age);
        ListUserVo userListResponse = ListUserVo.builder()
                .list(lst)
                .build();
        return CommonResult.success(userListResponse, "users returned");
    }

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
