package com.example.demo.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserDto {
    private Long id;
    private String name;
    private LocalDate dob;
    private Integer sex;
    private LocalDateTime createTime;
    private Boolean Administrator;
    private String token;

}
