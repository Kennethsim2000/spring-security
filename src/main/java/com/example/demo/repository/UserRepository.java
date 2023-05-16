package com.example.demo.repository;

import com.example.demo.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByName(String name);
    List<User> findBySex(String sex);

    @Query("SELECT u FROM User u WHERE u.age >= :age")
    List<User> findByAge(int age);
}
