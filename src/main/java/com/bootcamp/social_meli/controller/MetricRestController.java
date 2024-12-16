package com.bootcamp.social_meli.controller;

import com.bootcamp.social_meli.dto.response.MostFollowersResponseDTO;
import com.bootcamp.social_meli.dto.response.MostProductsResponseDTO;
import com.bootcamp.social_meli.dto.response.MostPostsUsersResponseDTO;
import com.bootcamp.social_meli.dto.response.UserDetailsDTO;
import com.bootcamp.social_meli.service.IProductService;
import com.bootcamp.social_meli.service.IPostService;
import com.bootcamp.social_meli.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/metrics")
public class MetricRestController {

    private final IUserService userService;
    private final IPostService postService;
    private final IProductService productService;

    @Autowired
    public MetricRestController(IUserService userService, IPostService postService, IProductService productService) {
        this.userService = userService;
        this.postService = postService;
        this.productService = productService;
    }

    @GetMapping("/top/most_followers")
    public ResponseEntity<MostFollowersResponseDTO> getMostFollowersUsers(@RequestParam(required = false) Integer rank) {
        if(rank != null) {
            return ResponseEntity.ok(userService.mostFollowers(rank));
        } else {
            return ResponseEntity.ok(userService.mostFollowers());
        }
    }
    @GetMapping("/{userId}/details")
    public ResponseEntity<UserDetailsDTO> getUserDetails(@PathVariable Long userId) {
            return ResponseEntity.ok(userService.metricsUserDetails(userId));
    }

    @GetMapping("/top/most_products")
    public ResponseEntity<MostProductsResponseDTO> getMostProductsPosted(@RequestParam(required = false) String rank){
        if(rank != null) {
            return ResponseEntity.ok(productService.getMostProducts(rank));
        } else {
            return ResponseEntity.ok(productService.getMostProducts());
        }
    }
    @GetMapping("/top/most_posts")
    public ResponseEntity<MostPostsUsersResponseDTO> getMostsPostsUsers(@RequestParam(required = false) Integer rank) {
        if(rank != null) {
            return ResponseEntity.ok(postService.mostPostsUsers(rank));
        } else {
            return ResponseEntity.ok(postService.mostPostsUsers());
        }
    }
}
