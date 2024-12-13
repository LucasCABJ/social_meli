package com.bootcamp.social_meli.controller;

import com.bootcamp.social_meli.dto.SimpleMessageDTO;
import com.bootcamp.social_meli.dto.response.AmountOfPromosDTO;
import com.bootcamp.social_meli.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductRestController {

    @Autowired
    private IProductService userService;

    @GetMapping("/promo-post/count")
    public ResponseEntity<?> getAmountOfPromosByUser(@RequestParam Long user_id){
        return ResponseEntity.ok(userService.getAmountOfPromosByUser(user_id));
    }
}
