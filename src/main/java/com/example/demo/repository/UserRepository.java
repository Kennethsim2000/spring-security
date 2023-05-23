package com.example.demo.repository;

import com.example.demo.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);

    List<User> findBySex(Integer sex);

    Boolean existsByName(String name);

    @Query("SELECT u FROM User u WHERE u.dob BETWEEN :startDate AND :endDate")
    List<User> findByDob(LocalDate startDate, LocalDate endDate);

    @Query("SELECT u FROM User u WHERE u.name = :name AND u.password = :password")
    User findByNameAndPassword(String name, String password);

}
