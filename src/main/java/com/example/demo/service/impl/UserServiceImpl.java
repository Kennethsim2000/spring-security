package com.example.demo.service.impl;

import com.example.demo.UserNotFoundException;
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
    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    //add a user
    @Override
    public User addUser(User user) {
        User newUser = new User();
        newUser.setAge(user.getAge());
        newUser.setName(user.getName());
        newUser.setSex(user.getSex());
        newUser.setAdministrator(false);
        newUser.setCreateTime(LocalDateTime.now());
        newUser.setPassword(user.getPassword());
        return userRepository.save(newUser);
    }

    //delete the user based on id
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
