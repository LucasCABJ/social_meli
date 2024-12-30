package com.bootcamp.social_meli.unit.service;

import com.bootcamp.social_meli.model.User;
import com.bootcamp.social_meli.repository.IPostRepository;
import com.bootcamp.social_meli.repository.IUserRepository;
import com.bootcamp.social_meli.repository.impl.PostRepositoryImpl;
import com.bootcamp.social_meli.repository.impl.UserRepositoryImpl;
import com.bootcamp.social_meli.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    IUserRepository userRepository;
    @Mock
    IPostRepository postRepository;
    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    UserServiceImpl userService;

    @Autowired
    private UserServiceImpl userServiceImpl;

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
        UserServiceImpl userService = new UserServiceImpl(postRepository, userRepository, objectMapper);

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