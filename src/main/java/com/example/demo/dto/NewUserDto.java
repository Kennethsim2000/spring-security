package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class NewUserDto {
    private String name;
    private int age;
    private String sex;
    private String password;
}
