package com.example.demo.vo;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemStatsAgeVo {
    private Integer age;
    private String itemCategory;
    private Long count;
}
