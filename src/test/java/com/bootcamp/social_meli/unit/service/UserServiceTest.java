package com.bootcamp.social_meli.unit.service;

import com.bootcamp.social_meli.dto.response.FollowedListResponseDTO;
import com.bootcamp.social_meli.dto.response.FollowersListResponseDTO;
import com.bootcamp.social_meli.exception.BadRequestException;
import com.bootcamp.social_meli.exception.NotFoundException;
import com.bootcamp.social_meli.model.User;
import com.bootcamp.social_meli.repository.IUserRepository;
import com.bootcamp.social_meli.service.impl.UserServiceImpl;
import com.bootcamp.social_meli.utils.UserGenerator;
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
    @DisplayName("findFollowersList: El usuario debe poder obtener la lista de seguidores")
    void  testFindFollowersList() {
        // Arrange
        Long userId = 1L;
        User user = UserGenerator.userWithFollowersAndeFollowed(userId);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Act
        FollowersListResponseDTO followersList = userService.findFollowersList(userId);
        // Assert
        Assertions.assertEquals(3, followersList.getFollowers().size());
    }

    @Test
    @DisplayName("findFollowersList: Debe arrojar NotFoundException si no " +
            "encuentra al usuario")
    void  testFindFollowersListUserNotFound() {
        // Arrange
        Long userId = 3L;
        Mockito.when(userRepository.findById(3L)).thenReturn(Optional.empty());
        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            userService.findFollowersList(userId);
        });
    }

    @Test
    void findFollowersList() {
    }

    @Test
    @DisplayName("findFollowersListOrdered: El usuario debe poder obtener la lista de seguidores " +
            "ordenada asendente")
    void findFollowersListOrderedAsc() {
        Long userId = 1L;
        User user = UserGenerator.userWithFollowersAndeFollowed(userId);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Act
        FollowersListResponseDTO followersList = userService.findFollowersList(userId, "name_asc");
        // Assert
        Assertions.assertEquals(3, followersList.getFollowers().size());
        Assertions.assertEquals("aarnold", followersList.getFollowers().get(0).getUser_name());
        Assertions.assertEquals("msalah", followersList.getFollowers().get(1).getUser_name());
        Assertions.assertEquals("tarnold", followersList.getFollowers().get(2).getUser_name());
    }
    @Test
    @DisplayName("findFollowersListOrdered: El usuario debe poder obtener la lista de seguidores " +
            "ordenada desendente")
    void findFollowersListOrderedDesc() {
        // Arrange
        Long userId = 1L;
        User user = UserGenerator.userWithFollowersAndeFollowed(userId);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Act
        FollowersListResponseDTO followersList = userService.findFollowersList(userId, "name_desc");
        // Assert
        Assertions.assertEquals(3, followersList.getFollowers().size());
        Assertions.assertEquals("tarnold", followersList.getFollowers().get(0).getUser_name());
        Assertions.assertEquals("msalah", followersList.getFollowers().get(1).getUser_name());
        Assertions.assertEquals("aarnold", followersList.getFollowers().get(2).getUser_name());
    }

    @Test
    @DisplayName("findFollowersListOrdered: Debe arrojar NotFoundException si no " +
            "encuentra al usuario")
    void findFollowersListOrderedUserNotFound() {
        // Arrange
        Long userId = 3L;
        Mockito.when(userRepository.findById(3L)).thenReturn(Optional.empty());
        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            userService.findFollowersList(userId, "name_asc");
        });
    }

    @Test
    @DisplayName("findFollowersListOrdered: El usuario debe poder dejar de seguir a otros.")
    void findFollowersListOrderedInvalidOrderParameter() {
        // Arrange
        Long userId = 1L;
        User user = UserGenerator.userWithFollowersAndeFollowed(userId);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Act & Assert
        Assertions.assertThrows(BadRequestException.class, () -> {
            userService.findFollowersList(userId, "asendente");
        });
    }

    @Test
    @DisplayName("findFollowedListOrdered: El usuario debe poder obtener la lista de seguidores " +
            "ordenada desendente")
    void findFollowedList() {
        // Arrange
        Long userId = 1L;
        User user = UserGenerator.userWithFollowersAndeFollowed(userId);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Act
        FollowedListResponseDTO followedList = userService.findFollowedList(userId);
        // Assert
        Assertions.assertEquals(3, followedList.getFollowed().size());
    }

    @Test
    @DisplayName("findFollowedListOrdered: Debe arrojar NotFoundException si no " +
            "encuentra al usuario")
    void findFollowedListUserNotFound() {
        // Arrange
        Long userId = 3L;
        Mockito.when(userRepository.findById(3L)).thenReturn(Optional.empty());
        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            userService.findFollowedList(userId);
        });
    }

    @Test
    @DisplayName("findFollowedListOrdered: El usuario debe poder obtener la lista de seguidores " +
            "ordenada asendente")
    void findFollowedListOrderedAsc() {
        Long userId = 1L;
        User user = UserGenerator.userWithFollowersAndeFollowed(userId);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Act
        FollowedListResponseDTO followedList = userService.findFollowedList(userId, "name_asc");
        // Assert
        Assertions.assertEquals(3, followedList.getFollowed().size());
        Assertions.assertEquals("aarnold", followedList.getFollowed().get(0).getUser_name());
        Assertions.assertEquals("msalah", followedList.getFollowed().get(1).getUser_name());
        Assertions.assertEquals("smane", followedList.getFollowed().get(2).getUser_name());
    }
    @Test
    @DisplayName("findFollowedListOrdered: El usuario debe poder obtener la lista de seguidores " +
            "ordenada desendente")
    void findFollowedListOrderedDesc() {
        // Arrange
        Long userId = 1L;
        User user = UserGenerator.userWithFollowersAndeFollowed(userId);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Act
        FollowedListResponseDTO followedList = userService.findFollowedList(userId, "name_desc");
        // Assert
        Assertions.assertEquals(3, followedList.getFollowed().size());
        Assertions.assertEquals("smane", followedList.getFollowed().get(0).getUser_name());
        Assertions.assertEquals("msalah", followedList.getFollowed().get(1).getUser_name());
        Assertions.assertEquals("aarnold", followedList.getFollowed().get(2).getUser_name());
    }

    @Test
    @DisplayName("findFollowedListOrdered: Debe arrojar NotFoundException si no " +
            "encuentra al usuario")
    void findFollowedListOrderedUserNotFound() {
        // Arrange
        Long userId = 3L;
        Mockito.when(userRepository.findById(3L)).thenReturn(Optional.empty());
        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> {
            userService.findFollowedList(userId, "name_asc");
        });
    }

    @Test
    @DisplayName("findFollowedListOrdered: El usuario debe poder dejar de seguir a otros.")
    void findFollowedListOrderedInvalidOrderParameter() {
        // Arrange
        Long userId = 1L;
        User user = UserGenerator.userWithFollowersAndeFollowed(userId);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Act & Assert
        Assertions.assertThrows(BadRequestException.class, () -> {
            userService.findFollowedList(userId, "asendente");
        });
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