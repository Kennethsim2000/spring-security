package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class FilterDto {
    private String name;
    private Integer gender;
    private LocalDate startDate;

    private LocalDate endDate;

    private Integer pageNumber;

}
