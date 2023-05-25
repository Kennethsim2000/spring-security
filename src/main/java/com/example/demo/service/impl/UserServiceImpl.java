package com.example.demo.service.impl;

import com.example.demo.UserNotFoundException;
import com.example.demo.dto.NewUserDto;
import com.example.demo.dto.UserDto;
import com.example.demo.models.User;
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
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public List<User> findByName(String name, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        return userRepository.findByName(name, pageable);
    }

    @Override
    public List<User> findBySex(Integer gender, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        return userRepository.findBySex(gender, pageable);
    }

    @Override
    public List<User> findByDob(LocalDate startDate, LocalDate endDate, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        return userRepository.findByDob(startDate, endDate, pageable);
    }


    //return all users
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllUsers(int page) {
        Pageable pageable = PageRequest.of(page - 1, 5);
        return userRepository.findAll(pageable).getContent();
    }

    @Override
    public List<ItemStatsAgeVo> groupUserByDobAndItemCategory() {
        List<ItemStatsDobVo> statsDobList = userRepository.groupUserByDobAndItemCategory();
        List<ItemStatsAgeVo> statsAgeList = new ArrayList<>();
        for (ItemStatsDobVo statsDob : statsDobList) {
            LocalDate currentDate = LocalDate.now();
            Integer age = Period.between(statsDob.getDob(), currentDate).getYears();
            ItemStatsAgeVo statsAge = new ItemStatsAgeVo();
            BeanUtils.copyProperties(statsDob, statsAge);
            statsAge.setAge(age);
            if(!presentInList(statsAgeList, statsAge)) {
                statsAgeList.add(statsAge);
            }
        }
        return statsAgeList;
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
    public User addUser(NewUserDto user) {
        if(userRepository.existsByName(user.getName())) {
            return null;
        }
        User newUser = new User();
        newUser.setDob(user.getDob());
        newUser.setName(user.getName());
        newUser.setSex(user.getSex());
        newUser.setAdministrator(false);
        newUser.setCreateTime(LocalDateTime.now());
        newUser.setPassword(user.getPassword());
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(UserDto user) {
        User selectedUser =  userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(user.getId()));
        selectedUser.setName(user.getName());
        selectedUser.setDob(user.getDob());
        selectedUser.setSex(user.getSex());
        selectedUser.setAdministrator(user.getAdministrator());
        return userRepository.save(selectedUser);
    }


    //delete the user based on id
    @Override
    public User deleteUser(long id) {
        User user = userRepository.findById(id).orElse(null);
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
    public User deleteById(long id) {
        Optional<User> deletedUser = userRepository.findById(id);
        userRepository.deleteById(id);
        return deletedUser.orElse(null);
    }

    @Override
    public User findUser(String name, String password) {
        return userRepository.findByNameAndPassword(name, password);
    }

}
