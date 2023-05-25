package com.example.demo.vo;

import lombok.*;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemStatsDobVo {
    private LocalDate dob;
    private String itemCategory;
    private Long count;


}
