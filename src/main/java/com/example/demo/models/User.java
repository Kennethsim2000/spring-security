package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    public User() {
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dob;

    private Integer sex; //1 is male, 0 is female

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime CreateTime;

    private Boolean Administrator;

    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Item> items;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", sex='" + sex + '\'' +
                ", CreateTime=" + CreateTime +
                ", Administrator=" + Administrator +
                ", password=" + password +
                '}';
    }
}
