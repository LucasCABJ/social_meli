package com.bootcamp.social_meli.unit.service;

import com.bootcamp.social_meli.model.User;
import com.bootcamp.social_meli.repository.IUserRepository;
import com.bootcamp.social_meli.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    IUserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;


    @Test
    void findAll() {
    }

    @Test
    void followUser() {
    }

    @Test
    void unfollowUser() {
    }

    @Test
    void findFollowersList() {
    }

    @Test
    void findFollowedList() {
    }

    @Test
    void testFindFollowersList() {
    }

    @Test
    void testFindFollowedList() {
    }

    @Test
    void getFollowerCountOfUserWithoutFollowersReturnsZero() {
        Long expectedAmount = 0L;
        User user = new User(1L, "Juan", "Juan", "juancito", List.of(), List.of());
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        Long amount = userService.getFollowerCount(1L).getFollowers_count();

        Assertions.assertEquals(expectedAmount, amount);
    }

    @Test
    void getFollowerCountOfUserWithTwoFollowersReturnsTwo() {
        Long expectedAmount = 2L;
        User follower1 = new User(2L, "Tom", "Tom", "tomito", List.of(), List.of());
        User follower2 = new User(2L, "Mati", "Mati", "mati123", List.of(), List.of());
        User user = new User(1L, "Juan", "Juan", "juancito", List.of(), List.of(follower1,follower2));
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        Long amount = userService.getFollowerCount(1L).getFollowers_count();

        Assertions.assertEquals(expectedAmount, amount);
    }

    @Test
    void mostFollowers() {
    }

    @Test
    void testMostFollowers() {
    }

    @Test
    void metricsUserDetails() {
    }

    @Test
    void createUser() {
    }
}