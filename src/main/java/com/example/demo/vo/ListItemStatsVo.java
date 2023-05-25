package com.example.demo.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListItemStatsVo {
    private List<ItemStatsAgeVo> list;
}
