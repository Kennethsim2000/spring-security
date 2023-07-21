package com.example.demo.service.impl;

import com.example.demo.UserNotFoundException;
import com.example.demo.dto.NewUserDto;
import com.example.demo.dto.UserDto;
import com.example.demo.models.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.vo.ItemStatsAgeVo;
import com.example.demo.vo.ItemStatsDobVo;
import org.springframework.beans.BeanUtils;
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
    private UserRepository userRepository;
    @Override
    public UserEntity findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }



    @Override
    public List<UserEntity> findBySex(Integer gender, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        return userRepository.findBySex(gender, pageable);
    }

    @Override
    public List<UserEntity> findByDob(LocalDate startDate, LocalDate endDate, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        return userRepository.findByDob(startDate, endDate, pageable);
    }


    //return all users
    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserEntity> findAllUsers(int page) {
        Pageable pageable = PageRequest.of(page - 1, 5);
        return userRepository.findAll(pageable).getContent();
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
        if(userRepository.existsByName(user.getName())) {
            return null;
        }
        UserEntity newUser = new UserEntity();
        newUser.setDob(user.getDob());
        newUser.setName(user.getName());
        newUser.setSex(user.getSex());
        newUser.setAdministrator(false);
        newUser.setCreateTime(LocalDateTime.now());
        newUser.setPassword(user.getPassword());
        return userRepository.save(newUser);
    }

    @Override
    public UserEntity updateUser(UserDto user) {
        UserEntity selectedUser =  userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(user.getId()));
        selectedUser.setName(user.getName());
        selectedUser.setDob(user.getDob());
        selectedUser.setSex(user.getSex());
        selectedUser.setAdministrator(user.getAdministrator());
        return userRepository.save(selectedUser);
    }


    //delete the user based on id
    @Override
    public UserEntity deleteUser(long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        userRepository.deleteById(id);
        return user;
    }

    //returns if such a user exist
    @Override
    public Boolean existsById(long id) {
        return userRepository.existsById(id);
    }

    //Delete user by id and return the deleted user
    @Override
    public UserEntity deleteById(long id) {
        Optional<UserEntity> deletedUser = userRepository.findById(id);
        userRepository.deleteById(id);
        return deletedUser.orElse(null);
    }

    @Override
    public UserEntity findUser(String name, String password) {
        return userRepository.findByNameAndPassword(name, password);
    }

}
