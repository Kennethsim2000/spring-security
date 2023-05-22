package com.example.demo.vo;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    private long id;

    private String name;

    private LocalDate dob;

    private Integer sex;

    private LocalDateTime createTime;

    private Boolean administrator;

    private String password;


}
