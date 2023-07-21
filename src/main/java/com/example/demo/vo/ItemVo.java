package com.example.demo.vo;

import com.example.demo.models.UserEntity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemVo {
    private long id;
    private String itemName;
    private String itemDescription;
    private String itemCategory;
    private UserEntity user;

}
