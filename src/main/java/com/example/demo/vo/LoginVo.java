package com.example.demo.vo;

import com.example.demo.models.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginVo {
    private User loginUser;
    private String token;
}
