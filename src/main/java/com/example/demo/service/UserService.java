package com.example.demo.service;

import com.example.demo.dto.NewUserDto;
import com.example.demo.dto.UserDto;
import com.example.demo.models.User;

import java.util.List;

public interface UserService {
    User findById(Long userId);

    List<User> findByName(String name);
    List<User> findBySex(String sex);
    List<User> findByAge(int age);
    Iterable<User> findAllUsers();
    User addUser(NewUserDto user);

    User updateUser(UserDto user);
    String deleteUser(long id);
    Boolean existsById(long id);

    User deleteById(long id);


}
