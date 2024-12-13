package com.bootcamp.social_meli.controller;

import com.bootcamp.social_meli.dto.response.PostsFromFollowsDTO;
import com.bootcamp.social_meli.service.IProductService;
import com.bootcamp.social_meli.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductRestController {
    private final IProductService productService;
    public ProductRestController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/followed/{userId}/list")
    public ResponseEntity<PostsFromFollowsDTO> getAllPostsFollowsLastTwoWeeks(@PathVariable Long userId, @RequestParam(defaultValue = "date_asc") String order)
    {
        return new ResponseEntity<>(productService.getAllPostsFollowsLastTwoWeeks(userId, order), HttpStatus.OK);
    }
}
