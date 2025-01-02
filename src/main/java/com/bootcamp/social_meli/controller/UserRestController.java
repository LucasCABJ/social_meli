package com.bootcamp.social_meli.controller;

import com.bootcamp.social_meli.controller.swagger.IUserRestController;
import com.bootcamp.social_meli.dto.request.UserDTO;
import com.bootcamp.social_meli.dto.request.CreateUserRequestDTO;
import com.bootcamp.social_meli.dto.response.FollowedListResponseDTO;
import com.bootcamp.social_meli.dto.response.FollowersListResponseDTO;
import com.bootcamp.social_meli.dto.response.FollowerCountResponse;
import com.bootcamp.social_meli.dto.response.SimpleMessageResponseDTO;
import com.bootcamp.social_meli.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Gestión de Usuarios", description = "Operaciones relacionadas con los usuarios.")
public class UserRestController implements IUserRestController {

    private final IUserService userService;

    @Autowired
    public UserRestController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("{userId}/follow/{userToFollowId}")
    public ResponseEntity<SimpleMessageResponseDTO> followUser(@PathVariable Long userId, @PathVariable Long userToFollowId) {
        return ResponseEntity.ok(new SimpleMessageResponseDTO(userService.followUser(userId, userToFollowId)));
    }

    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<FollowerCountResponse> getFollowersCount(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getFollowerCount(userId));
    }

    @GetMapping("/{userId}/followers/list")
    public ResponseEntity<FollowersListResponseDTO> findFollowerList(@PathVariable  Long userId, @RequestParam(required = false) String order) {
        return order != null && !order.isEmpty() ?
                ResponseEntity.ok(userService.findFollowersList(userId, order)) :
                ResponseEntity.ok(userService.findFollowersList(userId));
    }

    @GetMapping("/{userId}/followed/list")
    public ResponseEntity<FollowedListResponseDTO> findFollowedList(@PathVariable  Long userId, @RequestParam(required = false) String order) {
        return order != null && !order.isEmpty() ?
                ResponseEntity.ok(userService.findFollowedList(userId, order)) :
                ResponseEntity.ok(userService.findFollowedList(userId));
    }

    @PostMapping("{userId}/unfollow/{userToUnfollowId}")
    public ResponseEntity<SimpleMessageResponseDTO> unfollowUser(@PathVariable Long userId, @PathVariable Long userToUnfollowId) {
        return ResponseEntity.ok(new SimpleMessageResponseDTO(userService.unfollowUser(userId, userToUnfollowId)));
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid CreateUserRequestDTO user) {
        return ResponseEntity.ok(userService.createUser(user));
    }
}