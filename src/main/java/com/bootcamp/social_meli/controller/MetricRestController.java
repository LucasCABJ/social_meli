package com.bootcamp.social_meli.controller;

import com.bootcamp.social_meli.dto.response.MostFollowersResponseDTO;
import com.bootcamp.social_meli.dto.response.MostPostsUsersResponseDTO;
import com.bootcamp.social_meli.service.IPostService;
import com.bootcamp.social_meli.service.IUserService;
import com.bootcamp.social_meli.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metrics")
public class MetricRestController {

    private final IUserService userService;

    private final IPostService postService;

    @Autowired
    public MetricRestController(IUserService userService, IPostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/top/most_followers")
    public ResponseEntity<MostFollowersResponseDTO> getMostFollowersUsers(@RequestParam(required = false) Integer rank) {
        if(rank != null) {
            return ResponseEntity.ok(userService.mostFollowers(rank));
        } else {
            return ResponseEntity.ok(userService.mostFollowers());
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
