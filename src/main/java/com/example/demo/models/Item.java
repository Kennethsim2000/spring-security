package com.example.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String itemName;

    private String itemDescription;

    private String itemCategory;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;


}
