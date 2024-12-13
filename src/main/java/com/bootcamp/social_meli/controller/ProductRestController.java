package com.bootcamp.social_meli.controller;

import com.bootcamp.social_meli.dto.PostDTO;
import com.bootcamp.social_meli.dto.response.UserPostResponse;
import com.bootcamp.social_meli.service.IPostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductRestController {

    @Autowired
    private IPostService postService;

    @PostMapping("/post")
    public ResponseEntity<?> postProduct(@Valid @RequestBody PostDTO postDTO) {
        UserPostResponse createPostResponse = postService.createPost(postDTO);
        return ResponseEntity.ok(createPostResponse);
    }
}
