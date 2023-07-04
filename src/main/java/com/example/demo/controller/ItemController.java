package com.example.demo.controller;

import com.example.demo.config.CommonResult;
import com.example.demo.dto.ItemDto;
import com.example.demo.service.impl.ItemServiceImpl;
import com.example.demo.vo.ItemVo;
import com.example.demo.vo.UserVo;
import com.example.demo.models.Item;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/item")
@RestController
public class ItemController {
    @Autowired
    private ItemServiceImpl itemServiceImpl;
    @PostMapping("/add")
    public CommonResult<ItemVo> addUser(@RequestBody ItemDto item) {
        //hello
        //test
        //和嗯咯
        Item newItem = itemServiceImpl.addItem(item);
        if(newItem == null) {
            return CommonResult.failed(404,"Item already exist");
        }
        ItemVo itemResponse = new ItemVo();
        BeanUtils.copyProperties(newItem,itemResponse);
        return CommonResult.success(itemResponse, "Item added");
    }
}
