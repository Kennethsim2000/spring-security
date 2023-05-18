package com.example.demo.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserDto {
    private long id;
    private String name;
    private int age;
    private String sex;
    private LocalDateTime createTime;
    private boolean Administrator;
    public void setAdministrator(boolean administrator) {
        Administrator = administrator;
    }
    public boolean getAdministrator() {
        return Administrator;
    }
}
