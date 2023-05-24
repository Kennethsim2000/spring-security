package com.example.demo.dto;

import com.example.demo.models.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemDto {
    private String itemName;
    private String itemDescription;
    private String itemCategory;
    private Long userId;
}
