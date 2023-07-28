package com.example.demo.controller;

import com.example.demo.config.CommonResult;
import com.example.demo.dto.*;
import com.example.demo.models.UserEntity;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.utils.CustomAuthenticationProvider;
import com.example.demo.utils.JwtTokenProvider;
import com.example.demo.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private CustomAuthenticationProvider authenticator;

    @PostMapping("/add")
    public CommonResult<UserVo> addUser(@RequestBody NewUserDto user) {
        UserEntity newUser = userServiceImpl.addUser(user);
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
        List<UserEntity> lst = userServiceImpl.findAllUsers();
        ListUserVo userListResponse = ListUserVo.builder()
                .list(lst)
                .build();
        return CommonResult.success(userListResponse, "users returned");
    }


    @GetMapping("/getPage")
    public CommonResult<ListUserVo> getPage(@RequestParam("page") int page) {
        List<UserEntity> lst = userServiceImpl.findAllUsers(page);
        ListUserVo userListResponse = ListUserVo.builder()
                .list(lst)
                .build();
        return CommonResult.success(userListResponse, "users returned");
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path="/delete") // Map ONLY DELETE Requests
    public CommonResult<UserVo> deleteUser (@RequestBody DeletedUserDto deletedUser) {
        if(userServiceImpl.existsById(deletedUser.getId())){ //if user exists
            UserEntity user = userServiceImpl.deleteUser(deletedUser.getId());
            UserVo userResponse = new UserVo();
            BeanUtils.copyProperties(user,userResponse);
            return CommonResult.success(userResponse, "User deleted successfully");
        } else {
            return CommonResult.failed(404,"User does not exist");
        }
    }

    @PostMapping("/getByGender")
    public CommonResult<ListUserVo> getByGender(@RequestBody FilterDto filter ) {
        List<UserEntity> lst =  userServiceImpl.findBySex(filter.getGender(), filter.getPageNumber());
        ListUserVo userListResponse = ListUserVo.builder()
                .list(lst)
                .build();
        return CommonResult.success(userListResponse, "users returned");
    }

    @PostMapping("/getByDob")
    public CommonResult<ListUserVo> getByDob(@RequestBody FilterDto filter ) {
        System.out.println(filter);
        List<UserEntity> lst = userServiceImpl.findByDob(filter.getStartDate(), filter.getEndDate(), filter.getPageNumber());
        ListUserVo userListResponse = ListUserVo.builder()
                .list(lst)
                .build();
        return CommonResult.success(userListResponse, "users returned");
    }

    @PutMapping("/update")
    public CommonResult<UserVo> updateUser(@RequestBody UserDto user) {
        UserEntity updatedUser = userServiceImpl.updateUser(user);
        System.out.println("updated user is " + updatedUser);
        UserVo userResponse = new UserVo();
        BeanUtils.copyProperties(updatedUser,userResponse);
        return CommonResult.success(userResponse, "User updated successfully");
    }

    @GetMapping("test")
    public Integer hello() {
        System.out.println("test");
        return 1000;
    }

    //CommonResult<LoginVo>
    @PostMapping("/login")
    public Authentication getById(@RequestBody LoginUserDto loginUser) {
        UserEntity userFound = userServiceImpl.findUser(loginUser.getName(), loginUser.getPassword());
        String token = null;
        if(userFound != null) { //if login successful
             token = provider.generateToken(userFound);
        }
        LoginVo loginResponse = LoginVo.builder()
                .loginUser(userFound)
                .token(token)
                .build();
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userFound.getName(),userFound.getPassword());
        Authentication authenticate = authenticator.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        /*The SecurityContextHolder typically stores an instance of the SecurityContext, which
        contains the details of the currently authenticated user, their granted authorities
        (roles), and any other security-related information. */
        if(userFound!=null) {
            return authenticate;
        } else {
            return null;
        }
    }



}
