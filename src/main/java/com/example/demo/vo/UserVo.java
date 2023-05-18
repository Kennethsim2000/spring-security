package com.example.demo.vo;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    private long id;

    private String name;

    private int age;

    private String sex;

    private LocalDateTime createTime;

    private Boolean administrator;

    private String password;


}
