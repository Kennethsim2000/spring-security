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
        System.out.println(gender);
        return userRepository.findBySex(gender);
    }

    @Override
    public List<User> findByAge(int age) {
        return userRepository.findByAge(age);
    }

    //return all users
    @Override
    public Iterable<User> findAllUsers() {
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
        System.out.println(user.toString());
        System.out.println(selectedUser.toString());
        if (!selectedUser.getName().equals(user.getName())) {
            selectedUser.setName(user.getName());
        }
        if (selectedUser.getAge() != user.getAge()) {
            selectedUser.setAge(user.getAge());
        }
        if (!selectedUser.getSex().equals(user.getSex())) {
            selectedUser.setSex(user.getSex());
        }
        if (!selectedUser.getAdministrator().equals(user.getAdministrator())) {
            selectedUser.setAdministrator(user.getAdministrator());
        }
        System.out.println(selectedUser.toString());
        return userRepository.save(selectedUser);
    }


    //delete the user based on id
    @Override
    public String deleteUser(long id) {
        userRepository.deleteById(id);
        return "Tutor removed !! " + id;
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

    //Retrieve user by id
    public User findUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
