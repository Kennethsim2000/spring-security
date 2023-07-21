package com.example.demo.service;

import com.example.demo.dto.NewUserDto;
import com.example.demo.dto.UserDto;
import com.example.demo.models.UserEntity;
import com.example.demo.vo.ItemStatsAgeVo;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    UserEntity findById(Long userId);

    List<UserEntity> findBySex(Integer sex, int pageNumber);
    List<UserEntity> findByDob(LocalDate startDate, LocalDate endDate, int pageNumber);
    List<UserEntity> findAllUsers();
    UserEntity addUser(NewUserDto user);

    UserEntity updateUser(UserDto user);
    UserEntity deleteUser(long id);
    Boolean existsById(long id);

    UserEntity deleteById(long id);

    UserEntity findUser(String name, String password);
     List<UserEntity> findAllUsers(int page);


}
