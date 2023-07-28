package com.example.demo.service.impl;

import com.example.demo.UserNotFoundException;
import com.example.demo.dto.NewUserDto;
import com.example.demo.dto.UserDto;
import com.example.demo.models.UserEntity;
import com.example.demo.repository.UserEntityRepository;
import com.example.demo.service.UserService;
import com.example.demo.vo.ItemStatsAgeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserEntityRepository userEntityRepository;
    @Override
    public UserEntity findById(Long userId) {
        return userEntityRepository.findById(userId).orElse(null);
    }



    @Override
    public List<UserEntity> findBySex(Integer gender, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        return userEntityRepository.findBySex(gender, pageable);
    }

    @Override
    public List<UserEntity> findByDob(LocalDate startDate, LocalDate endDate, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        return userEntityRepository.findByDob(startDate, endDate, pageable);
    }


    //return all users
    @Override
    public List<UserEntity> findAllUsers() {
        return userEntityRepository.findAll();
    }

    @Override
    public List<UserEntity> findAllUsers(int page) {
        Pageable pageable = PageRequest.of(page - 1, 5);
        return userEntityRepository.findAll(pageable).getContent();
    }


    public boolean presentInList(List<ItemStatsAgeVo> lst,  ItemStatsAgeVo statsAge) {
        boolean present = false;
        for (ItemStatsAgeVo item : lst) {
            if (item.getAge().equals(statsAge.getAge()) && item.getItemCategory().equals(statsAge.getItemCategory())) {
                item.setCount(item.getCount() + statsAge.getCount());
                present = true;
            }
        }
        return present;
    }

    //add a user
    @Override
    public UserEntity addUser(NewUserDto user) {
        if(userEntityRepository.existsByName(user.getName())) {
            return null;
        }
        UserEntity newUser = new UserEntity();
        newUser.setDob(user.getDob());
        newUser.setName(user.getName());
        newUser.setSex(user.getSex());
        newUser.setAdministrator(false);
        newUser.setCreateTime(LocalDateTime.now());
        newUser.setPassword(user.getPassword());
        return userEntityRepository.save(newUser);
    }

    @Override
    public UserEntity updateUser(UserDto user) {
        UserEntity selectedUser =  userEntityRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(user.getId()));
        selectedUser.setName(user.getName());
        selectedUser.setDob(user.getDob());
        selectedUser.setSex(user.getSex());
        selectedUser.setAdministrator(user.getAdministrator());
        return userEntityRepository.save(selectedUser);
    }


    //delete the user based on id
    @Override
    public UserEntity deleteUser(long id) {
        UserEntity user = userEntityRepository.findById(id).orElse(null);
        userEntityRepository.deleteById(id);
        return user;
    }

    //returns if such a user exist
    @Override
    public Boolean existsById(long id) {
        return userEntityRepository.existsById(id);
    }

    //Delete user by id and return the deleted user
    @Override
    public UserEntity deleteById(long id) {
        Optional<UserEntity> deletedUser = userEntityRepository.findById(id);
        userEntityRepository.deleteById(id);
        return deletedUser.orElse(null);
    }

    @Override
    public UserEntity findUser(String name, String password) {
        List<UserEntity> lst = userEntityRepository.findAll();
        System.out.println(lst);
        return userEntityRepository.findByNameAndPassword(name, password);
    }

}
