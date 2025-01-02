package com.bootcamp.social_meli.unit.service;

import com.bootcamp.social_meli.exception.BadRequestException;
import com.bootcamp.social_meli.exception.NotFoundException;
import com.bootcamp.social_meli.model.User;
import com.bootcamp.social_meli.repository.IUserRepository;
import com.bootcamp.social_meli.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("El usuario debe poder dejar de seguir a otros.")
    void unfollowUser() {
        // Arrange
        Long userId = 1L;
        Long userIdToFollow = 3L;
        User user = new User(1L, "Robert", "Firminho", "firminho10", new ArrayList<>(), new ArrayList<>());
        User userToUnfollow = new User(3L, "Alexander", "Arnold", "aarnold", new ArrayList<>(), new ArrayList<>());
        user.getFollowed().add(userToUnfollow);
        userToUnfollow.getFollowers().add(user);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findById(3L)).thenReturn(Optional.of(userToUnfollow));
        String expectedReturnMessage = "¡El usuario " + user.getUsername() + " ha dejado de seguir a " + userToUnfollow.getUsername() + " exitosamente!";
        // Act
        String returnedMessage = userService.unfollowUser(userId, userIdToFollow);
        // Assert
        Assertions.assertEquals(expectedReturnMessage, returnedMessage);
        Assertions.assertEquals(0, user.getFollowed().size());
        Assertions.assertEquals(0, userToUnfollow.getFollowers().size());
    }

    @Test
    @DisplayName("El usuario no puede dejar de seguirse a si mismo")
    void unfollowUserThrowsExceptionIfUsersTriesToAutoUnfollow() {
        // Arrange
        Long userId = 1L;
        // Act & Assert
        Assertions.assertThrows(BadRequestException.class, () -> {
            userService.unfollowUser(userId, userId);
        });
    }

    @Test
    @DisplayName("El usuario no puede dejar de seguir a alguien que no sigue")
    void unfollowUserThrowsExceptionIfUsersTriesToUnfollowsUnfollowedAccount() {
        // Arrange
        Long userId = 1L;
        Long userToUnfollowId = 3L;
        User user = new User(1L, "Robert", "Firminho", "firminho10", new ArrayList<>(), new ArrayList<>());
        User userToUnfollow = new User(3L, "Alexander", "Arnold", "aarnold", new ArrayList<>(), new ArrayList<>());
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findById(3L)).thenReturn(Optional.of(userToUnfollow));

        // Act & Assert
        Assertions.assertThrows(BadRequestException.class, () -> {
            userService.unfollowUser(userId, userToUnfollowId);
        });
    }

    @Test
    @DisplayName("El usuario no puede dejar de seguir a si no se encuentra en su lista de seguidores")
    void unfollowUserThrowsExceptionIfUsersTriesToUnfollowsAUserThatDoesntHaveItAsFollower() {
        // Arrange
        Long userId = 1L;
        Long userToUnfollowId = 3L;
        User user = new User(1L, "Robert", "Firminho", "firminho10", new ArrayList<>(), new ArrayList<>());
        User userToUnfollow = new User(3L, "Alexander", "Arnold", "aarnold", new ArrayList<>(), new ArrayList<>());
        user.getFollowed().add(userToUnfollow);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findById(3L)).thenReturn(Optional.of(userToUnfollow));

        // Act & Assert
        Assertions.assertThrows(BadRequestException.class, () -> {
            userService.unfollowUser(userId, userToUnfollowId);
        });
    }

    @Test
    @DisplayName("Debe arrojar NotFoundException si no encuentra al usuario")
    void unfollowUserThrowsExceptionIfUserNotFound() {
        // Arrange
        Long userId = 1L;
        Long userToFollowId = 3L;
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            userService.unfollowUser(userId, userToFollowId);
        });
    }

    @Test
    @DisplayName("Debe arrojar NotFoundException si no encuentra al usuario a dejar de seguir")
    void unfollowUserThrowsExceptionIfUserToUnfollowNotFound() {
        // Arrange
        Long userId = 1L;
        Long userToFollowId = 3L;
        User user = new User(1L, "Robert", "Firminho", "firminho10", new ArrayList<>(), new ArrayList<>());
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findById(3L)).thenReturn(Optional.empty());
        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            userService.unfollowUser(userId, userToFollowId);
        });
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
    void getFollowerCount() {
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