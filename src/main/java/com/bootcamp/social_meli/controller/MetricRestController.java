package com.bootcamp.social_meli.controller;

import com.bootcamp.social_meli.dto.response.MostFollowersResponseDTO;
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

    @Autowired
    private IUserService userService;

    @GetMapping("/top/most_followers")
    public ResponseEntity<MostFollowersResponseDTO> getMostFollowersUsers(@RequestParam(required = false) Integer rank) {
        if(rank != null) {
            return ResponseEntity.ok(userService.mostFollowers(rank));
        } else {
            return ResponseEntity.ok(userService.mostFollowers());
        }
    }

}
