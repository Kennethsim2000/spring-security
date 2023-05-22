package com.example.demo.controller;

import com.example.demo.config.CommonResult;
import com.example.demo.dto.*;
import com.example.demo.models.User;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.utils.JwtTokenProvider;
import com.example.demo.vo.ListUserVo;
import com.example.demo.vo.LoginVo;
import com.example.demo.vo.UserVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import cn.hutool.crypto.digest.Digester;
import io.jsonwebtoken.security.Keys;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import cn.hutool.crypto.digest.DigestAlgorithm;


import java.security.Key;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private JwtTokenProvider provider;
    @PostMapping("/add")
    public CommonResult<UserVo> addUser(@RequestBody NewUserDto user) {
        User newUser = userServiceImpl.addUser(user);
        if(newUser == null) {
            return CommonResult.failed(404,"User exist already");
        }
        UserVo userResponse = UserVo.builder()
                .id(newUser.getId())
                .name(newUser.getName())
                .dob(newUser.getDob())
                .createTime(newUser.getCreateTime())
                .administrator(newUser.getAdministrator())
                .password(newUser.getPassword())
                .sex(newUser.getSex())
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
                .dob(user.getDob())
                .createTime(user.getCreateTime())
                .administrator(user.getAdministrator())
                .build();
        return CommonResult.success(userResponse, "User found");
    }

    @DeleteMapping(path="/delete") // Map ONLY DELETE Requests
    public CommonResult<UserVo> deleteUser (@RequestBody DeletedUserDto deletedUser) {
        long userId = provider.validateToken(deletedUser.getToken());
        if(userId != deletedUser.getId()) {
            return CommonResult.failed(401,"User not authorised");
        }
        if(userServiceImpl.existsById(deletedUser.getId())){ //if user exists
            User user = userServiceImpl.deleteUser(deletedUser.getId());
            UserVo userResponse = UserVo.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .dob(user.getDob())
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

    @PostMapping("/getByDob")
    public CommonResult<ListUserVo> getByDob(@RequestBody FilterDto filter ) {
        List<User> lst = userServiceImpl.findByDob(filter.getStartDate(), filter.getEndDate());
        ListUserVo userListResponse = ListUserVo.builder()
                .list(lst)
                .build();
        return CommonResult.success(userListResponse, "users returned");
    }

    @PutMapping("/update")
    public CommonResult<UserVo> updateUser(@RequestBody UserDto user) {
        long userId = provider.validateToken(user.getToken());
        if(userId != user.getId()) {
            return CommonResult.failed(401,"User not authorised");
        }
        User updatedUser = userServiceImpl.updateUser(user);
        UserVo userResponse = UserVo.builder()
                .id(updatedUser.getId())
                .name(updatedUser.getName())
                .dob(updatedUser.getDob())
                .createTime(updatedUser.getCreateTime())
                .administrator(updatedUser.getAdministrator())
                .build();
        return CommonResult.success(userResponse, "User updated successfully");
    }

    @PostMapping("/login")
    public CommonResult<LoginVo> getById(@RequestBody LoginUserDto loginUser) {
        User userFound = userServiceImpl.findUser(loginUser.getName(), loginUser.getPassword());
        String token = null;
        if(userFound != null) { //if login successful
             token = provider.generateToken(userFound, Keys.secretKeyFor(SignatureAlgorithm.HS256));
        }
        LoginVo loginResponse = LoginVo.builder()
                .loginUser(userFound)
                .token(token)
                .build();
        if(userFound!=null) {
            return CommonResult.success(loginResponse, "Login success");
        } else {
            return CommonResult.failed(404,"Login unsuccessful");
        }

    }


}
