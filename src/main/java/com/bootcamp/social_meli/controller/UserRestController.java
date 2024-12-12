package com.bootcamp.social_meli.controller;

import com.bootcamp.social_meli.dto.SimpleMessageDTO;
import com.bootcamp.social_meli.service.IUserService;
import com.bootcamp.social_meli.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private IUserService userService;

    @PostMapping("{userId}/follow/{userToFollowId}")
    public ResponseEntity<SimpleMessageDTO> followUser(@PathVariable Long userId, @PathVariable Long userToFollowId) {
        return ResponseEntity.ok(new SimpleMessageDTO(userService.followUser(userId, userToFollowId)));
    }

}
