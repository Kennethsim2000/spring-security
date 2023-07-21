package com.example.demo.vo;

import com.example.demo.models.UserEntity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginVo {
    private UserEntity loginUser;
    private String token;
}
