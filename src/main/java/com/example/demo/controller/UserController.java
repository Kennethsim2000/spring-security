package com.example.demo.controller;

import com.example.demo.config.CommonResult;
import com.example.demo.dto.*;
import com.example.demo.models.User;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.utils.JwtTokenProvider;
import com.example.demo.vo.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
@Slf4j
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
        UserVo userResponse = new UserVo();
        BeanUtils.copyProperties(newUser,userResponse);
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

    @GetMapping("/getItemStats")
    public CommonResult<ListItemStatsVo> getItemStats() {
        List<ItemStatsAgeVo> lst = userServiceImpl.groupUserByDobAndItemCategory();
        ListItemStatsVo itemsStatsListResponse = ListItemStatsVo.builder()
                .list(lst)
                .build();
        return CommonResult.success(itemsStatsListResponse, "Items stats returned");
    }

    @GetMapping("/getPage")
    public CommonResult<ListUserVo> getPage(@RequestParam("page") int page) {
        List<User> lst = userServiceImpl.findAllUsers(page);
        ListUserVo userListResponse = ListUserVo.builder()
                .list(lst)
                .build();
        return CommonResult.success(userListResponse, "users returned");
    }


    @DeleteMapping(path="/delete") // Map ONLY DELETE Requests
    public CommonResult<UserVo> deleteUser (@RequestBody DeletedUserDto deletedUser) {
        if(userServiceImpl.existsById(deletedUser.getId())){ //if user exists
            User user = userServiceImpl.deleteUser(deletedUser.getId());
            UserVo userResponse = new UserVo();
            BeanUtils.copyProperties(user,userResponse);
            return CommonResult.success(userResponse, "User deleted successfully");
        } else {
            return CommonResult.failed(404,"User does not exist");
        }
    }

    @PostMapping("/getByName")
    public CommonResult<ListUserVo> getByName(@RequestBody FilterDto filter ) {
        List<User> lst = userServiceImpl.findByName(filter.getName(), filter.getPageNumber());
        ListUserVo userListResponse = ListUserVo.builder()
                .list(lst)
                .build();
        return CommonResult.success(userListResponse, "users returned");
    }

    @PostMapping("/getByGender")
    public CommonResult<ListUserVo> getByGender(@RequestBody FilterDto filter ) {
        List<User> lst =  userServiceImpl.findBySex(filter.getGender(), filter.getPageNumber());
        ListUserVo userListResponse = ListUserVo.builder()
                .list(lst)
                .build();
        return CommonResult.success(userListResponse, "users returned");
    }

    @PostMapping("/getByDob")
    public CommonResult<ListUserVo> getByDob(@RequestBody FilterDto filter ) {
        System.out.println(filter);
        List<User> lst = userServiceImpl.findByDob(filter.getStartDate(), filter.getEndDate(), filter.getPageNumber());
        ListUserVo userListResponse = ListUserVo.builder()
                .list(lst)
                .build();
        return CommonResult.success(userListResponse, "users returned");
    }

    @PutMapping("/update")
    public CommonResult<UserVo> updateUser(@RequestBody UserDto user) {
        User updatedUser = userServiceImpl.updateUser(user);
        System.out.println("updated user is " + updatedUser);
        UserVo userResponse = new UserVo();
        BeanUtils.copyProperties(updatedUser,userResponse);
        return CommonResult.success(userResponse, "User updated successfully");
    }

    @PostMapping("/login")
    public CommonResult<LoginVo> getById(@RequestBody LoginUserDto loginUser) {
        User userFound = userServiceImpl.findUser(loginUser.getName(), loginUser.getPassword());
        String token = null;
        if(userFound != null) { //if login successful
             token = provider.generateToken(userFound);
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
//    @GetMapping("/getById")
//    @ResponseBody
//    public CommonResult<UserVo> getById(@RequestParam (value = "userId") long userId) {
//        User user = userServiceImpl.findById(userId);
//        UserVo userResponse = UserVo.builder()
//                .id(user.getId())
//                .name(user.getName())
//                .dob(user.getDob())
//                .createTime(user.getCreateTime())
//                .administrator(user.getAdministrator())
//                .build();
//        return CommonResult.success(userResponse, "User found");
//    }



}
