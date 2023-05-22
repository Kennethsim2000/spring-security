package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class NewUserDto {
    private String name;
    private LocalDate dob;
    private Integer sex;
    private String password;
}
