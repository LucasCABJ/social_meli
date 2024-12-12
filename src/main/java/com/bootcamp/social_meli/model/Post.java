package com.bootcamp.social_meli.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long id;
    private User creatorUser;
    private LocalDate createDate;
    private Product product;
    private Integer category;
    private Double price;
    private Boolean hasDiscount;
    private Double discountPercentage;
}
