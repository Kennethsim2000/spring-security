package com.example.demo.vo;

import com.example.demo.models.UserEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListUserVo {
    private List<UserEntity> list;
}
