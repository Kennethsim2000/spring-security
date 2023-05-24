package com.example.demo.service.impl;

import com.example.demo.dto.ItemDto;
import com.example.demo.models.Item;
import com.example.demo.models.User;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ItemService;
import org.apache.catalina.mbeans.MBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public Item addItem(ItemDto item) {
        if(itemRepository.existsByItemName(item.getItemName())) {
            return null;
        }
        User user = userRepository.findById(item.getUserId()).orElse(null);
        Item newItem = new Item();
        BeanUtils.copyProperties(item, newItem);
        newItem.setUser(user);
        return itemRepository.save(newItem);
    }
}
