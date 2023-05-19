package com.example.demo.service.impl;

import com.example.demo.UserNotFoundException;
import com.example.demo.dto.NewUserDto;
import com.example.demo.dto.UserDto;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List<User> findBySex(String gender) {
        return userRepository.findBySex(gender);
    }

    @Override
    public List<User> findByAge(int age) {
        return userRepository.findByAge(age);
    }

    //return all users
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    //add a user
    @Override
    public User addUser(NewUserDto user) {
        User newUser = new User();
        newUser.setAge(user.getAge());
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
        selectedUser.setAge(user.getAge());
        selectedUser.setSex(user.getSex());
        selectedUser.setAdministrator(user.getAdministrator());
        System.out.println(selectedUser.toString());
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
    public boolean findUser(String name, String password) {
        return userRepository.findByNameAndPassword(name, password);
    }

}
