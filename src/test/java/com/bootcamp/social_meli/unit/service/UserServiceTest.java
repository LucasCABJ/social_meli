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
    @DisplayName("El usuario debe poder seguir a otros.")
    void followUser() {
        // Arrange
        Long userId = 1L;
        Long userIdToFollow = 3L;
        User user = new User(1L, "Robert", "Firminho", "firminho10", new ArrayList<>(), new ArrayList<>());
        User userToFollow = new User(3L, "Alexander", "Arnold", "aarnold", new ArrayList<>(), new ArrayList<>());
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findById(3L)).thenReturn(Optional.of(userToFollow));
        String expectedReturnMessage = "¡El usuario " + user.getUsername() + " ha comenzado a seguir a " + userToFollow.getUsername() + " exitosamente!";
        // Act
        String returnedMessage = userService.followUser(userId, userIdToFollow);
        // Assert
        Assertions.assertEquals(expectedReturnMessage, returnedMessage);
        Assertions.assertEquals(1, user.getFollowed().size());
        Assertions.assertEquals(1, userToFollow.getFollowers().size());
    }

    @Test
    @DisplayName("El usuario no puede seguirse a si mismo")
    void followUserThrowsExceptionIfUsersTriesToAutofollow() {
        // Arrange
        Long userId = 1L;
        // Act & Assert
        Assertions.assertThrows(BadRequestException.class, () -> {
            userService.followUser(userId, userId);
        });
    }

    @Test
    @DisplayName("El usuario no puede seguir a alguien 2 veces")
    void followUserThrowsExceptionIfUsersTriesToFollowTwice() {
        // Arrange
        Long userId = 1L;
        Long userIdToFollow = 3L;
        User user = new User(1L, "Robert", "Firminho", "firminho10", new ArrayList<>(), new ArrayList<>());
        User userToFollow = new User(3L, "Alexander", "Arnold", "aarnold", new ArrayList<>(), new ArrayList<>());
        user.getFollowed().add(userToFollow);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findById(3L)).thenReturn(Optional.of(userToFollow));
        // Act & Assert
        Assertions.assertThrows(BadRequestException.class, () -> {
            userService.followUser(userId, userIdToFollow);
        });
    }

    @Test
    @DisplayName("El usuario no puede ser seguido 2 veces por la misma persona")
    void followUserThrowsExceptionIfUserIsFollowedTwice() {
        // Arrange
        Long userId = 1L;
        Long userIdToFollow = 3L;
        User user = new User(1L, "Robert", "Firminho", "firminho10", new ArrayList<>(), new ArrayList<>());
        User userToFollow = new User(3L, "Alexander", "Arnold", "aarnold", new ArrayList<>(), new ArrayList<>());
        userToFollow.getFollowers().add(user);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findById(3L)).thenReturn(Optional.of(userToFollow));
        // Act & Assert
        Assertions.assertThrows(BadRequestException.class, () -> {
            userService.followUser(userId, userIdToFollow);
        });
    }

    @Test
    @DisplayName("Debe arrojar NotFoundException si no encuentra al usuario")
    void followUserThrowsExceptionIfUserNotFound() {
        // Arrange
        Long userId = 1L;
        Long userToFollowId = 3L;
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            userService.followUser(userId, userToFollowId);
        });
    }

    @Test
    @DisplayName("Debe arrojar NotFoundException si no encuentra al usuario a seguir")
    void followUserThrowsExceptionIfUserToFollowNotFound() {
        // Arrange
        Long userId = 1L;
        Long userToFollowId = 3L;
        User user = new User(1L, "Robert", "Firminho", "firminho10", new ArrayList<>(), new ArrayList<>());
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findById(3L)).thenReturn(Optional.empty());
        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            userService.followUser(userId, userToFollowId);
        });
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