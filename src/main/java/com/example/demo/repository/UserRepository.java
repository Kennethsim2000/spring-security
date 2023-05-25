package com.example.demo.repository;

import com.example.demo.models.User;
import com.example.demo.vo.ItemStatsDobVo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name, Pageable pageable);

    List<User> findBySex(Integer sex, Pageable pageable);

    Boolean existsByName(String name);

    @Query("SELECT u FROM User u WHERE u.dob BETWEEN :startDate AND :endDate")
    List<User> findByDob(LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.name = :name AND u.password = :password")
    User findByNameAndPassword(String name, String password);

    @Query("SELECT new com.example.demo.vo.ItemStatsDobVo(u.dob, i.itemCategory, COUNT(i)) FROM User u JOIN u.items i GROUP BY u.dob, i.itemCategory ORDER BY u.dob DESC")
    List<ItemStatsDobVo> groupUserByDobAndItemCategory();



}
