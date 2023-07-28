package com.example.demo.repository;

import com.example.demo.models.UserEntity;
import com.example.demo.vo.ItemStatsDobVo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByName(String name);

    List<UserEntity> findBySex(Integer sex, Pageable pageable);

    Boolean existsByName(String name);

    @Query("SELECT u FROM UserEntity u WHERE u.dob BETWEEN :startDate AND :endDate")
    List<UserEntity> findByDob(LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query("SELECT u FROM UserEntity u WHERE u.name = :name AND u.password = :password")
    UserEntity findByNameAndPassword(String name, String password);



}
