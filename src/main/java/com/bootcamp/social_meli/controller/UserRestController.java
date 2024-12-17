package com.bootcamp.social_meli.controller;

import com.bootcamp.social_meli.dto.UserDTO;
import com.bootcamp.social_meli.dto.response.FollowedListDTO;
import com.bootcamp.social_meli.dto.response.FollowersListDTO;
import com.bootcamp.social_meli.dto.response.FollowerCountResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import com.bootcamp.social_meli.dto.SimpleMessageDTO;
import com.bootcamp.social_meli.service.IUserService;
import com.bootcamp.social_meli.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private IUserService userService;

    @Autowired
    public UserRestController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("{userId}/follow/{userToFollowId}")
    public ResponseEntity<SimpleMessageDTO> followUser(@PathVariable Long userId,
                                        @PathVariable Long userToFollowId) {
        return ResponseEntity.ok(new SimpleMessageDTO(userService.followUser(userId, userToFollowId)));
    }

    @PostMapping("{userId}/unfollow/{userToFollowId}")
    public ResponseEntity<SimpleMessageDTO> unfollowUser(@PathVariable Long userId,
                                          @PathVariable Long userToFollowId) {
        return ResponseEntity.ok(new SimpleMessageDTO(userService.unfollowUser(userId, userToFollowId)));
    }

    @GetMapping("/{userId}/followers/list")
    public ResponseEntity<FollowersListDTO> findFollowerList(@PathVariable String userId,
                                                             @RequestParam(required = false) String order) {
        if (order != null && !order.isEmpty()) {
            return ResponseEntity.ok(userService.findFollowersList(userId, order));
        } else {
            return ResponseEntity.ok(userService.findFollowersList(userId));
        }

    }

    @GetMapping("/{userId}/followed/list")
    public ResponseEntity<FollowedListDTO> findFollowedList(@PathVariable String userId,
                                                            @RequestParam(required = false) String order) {
        if (order != null && !order.isEmpty()) {
            return ResponseEntity.ok(userService.findFollowedList(userId, order));
        } else {
            return ResponseEntity.ok(userService.findFollowedList(userId));
        }
    }

    @GetMapping("{userId}/followers/count")
    public ResponseEntity<FollowerCountResponse> getFollowersCount(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getFollowerCount(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user){
        return ResponseEntity.ok(userService.createUser(user));
    }
}
