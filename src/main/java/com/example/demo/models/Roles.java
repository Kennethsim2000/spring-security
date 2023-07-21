package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String code;
    private String name;
    @ManyToMany(mappedBy = "userRoles")
    private List<UserEntity> users;
}
