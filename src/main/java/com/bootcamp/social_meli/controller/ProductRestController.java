package com.bootcamp.social_meli.controller;

import com.bootcamp.social_meli.dto.PostDTO;
import com.bootcamp.social_meli.dto.PromoPostDTO;
import com.bootcamp.social_meli.dto.response.AmountOfPromosDTO;
import com.bootcamp.social_meli.dto.response.PostsFromFollowsDTO;
import com.bootcamp.social_meli.dto.response.PostsWithProductDTO;
import com.bootcamp.social_meli.dto.response.UserPostResponse;
import com.bootcamp.social_meli.service.IPostService;
import com.bootcamp.social_meli.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductRestController {

    private IPostService postService;
    private IProductService productService;
    private IProductService userService;

    @Autowired
    public ProductRestController(IPostService postService, IProductService productService, IProductService userService) {
        this.postService = postService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/followed/{userId}/list")
    public ResponseEntity<PostsFromFollowsDTO> getAllPostsFollowsLastTwoWeeks(@PathVariable Long userId,
                                                                              @RequestParam(defaultValue = "date_asc") String order)
    {
        return new ResponseEntity<>(productService.getAllPostsFollowsLastTwoWeeks(userId, order), HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<UserPostResponse> postProduct(@Valid @RequestBody PostDTO postDTO) {
        return ResponseEntity.ok(postService.createPost(postDTO));
    }

    @PostMapping("/promo-post")
    public ResponseEntity<UserPostResponse> createPromoPost(@Valid @RequestBody PromoPostDTO promoPostDTO){
        return ResponseEntity.ok(postService.createPromoPost(promoPostDTO));
    }

    @GetMapping("/promo-post/count")
    public ResponseEntity<AmountOfPromosDTO> getAmountOfPromosByUser(@RequestParam Long user_id){
        return ResponseEntity.ok(userService.getAmountOfPromosByUser(user_id));
    }

    @GetMapping("/posts/search")
    public ResponseEntity<PostsWithProductDTO> getPostsWithProduct(@RequestParam String name){
        return ResponseEntity.ok(postService.getPostsWithProduct(name));
    }
}
