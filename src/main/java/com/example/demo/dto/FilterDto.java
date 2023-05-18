package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class FilterDto {
    private String name;
    private String gender;
    private LocalDateTime startData;

    private LocalDateTime endDate;

}
