package com.example.demo.vo;

import com.example.demo.models.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListUserVo {
    private List<User> list;
}
