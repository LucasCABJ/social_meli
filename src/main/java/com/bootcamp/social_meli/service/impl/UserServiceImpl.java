package com.bootcamp.social_meli.service.impl;

import com.bootcamp.social_meli.dto.UserDTO;
import com.bootcamp.social_meli.dto.response.FollowedListDTO;
import com.bootcamp.social_meli.dto.response.FollowersListDTO;
import com.bootcamp.social_meli.dto.response.SimpleUserDTO;
import com.bootcamp.social_meli.dto.response.FollowerCountResponse;
import com.bootcamp.social_meli.exception.BadRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import com.bootcamp.social_meli.exception.UserNotFoundException;
import com.bootcamp.social_meli.model.User;
import com.bootcamp.social_meli.repository.IUserRepository;
import com.bootcamp.social_meli.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> objectMapper.convertValue(user, UserDTO.class))
                .toList();
    }

    @Override
    public String followUser(Long userId, Long userToFollowId) {
        if(userId.equals(userToFollowId)) {
            throw new BadRequestException("El usuario no puede seguirse a si mismo.");
        }

        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<User> optionalUserToFollow = userRepository.findById(userToFollowId);

        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("No se ha encontrado al usuario: " + userId);
        }

        if(optionalUserToFollow.isEmpty()) {
            throw new UserNotFoundException("No se ha encontrado al usuario: " + userToFollowId);
        }

        User user = optionalUser.get();
        User userToFollow = optionalUserToFollow.get();

        List<User> userFollowedList = user.getFollowed();
        List<User> userToFollowFollowersList = userToFollow.getFollowers();

        if(userFollowedList.stream().anyMatch(u -> u.getId().equals(userToFollowId))) {
            throw new BadRequestException("El usuario " + user.getUsername() + " ya sigue al usuario " + userToFollow.getUsername());
        } else if (userToFollowFollowersList.stream().anyMatch(u -> u.getId().equals(userId))) {
            throw new BadRequestException("El usuario " + user.getUsername() + " ya se encuentra en la lista de seguidores de: " + userToFollow.getUsername());
        }

        userFollowedList.add(userToFollow);
        userToFollowFollowersList.add(user);

        return "¡El usuario " + user.getUsername() + " ha comenzado a seguir a " + userToFollow.getUsername() + " exitosamente!";
    }

    @Override
    public String unfollowUser(Long userId, Long userToFollowId) {
        if(userId.equals(userToFollowId)) {
            throw new BadRequestException("El usuario no puede dejarse de seguir a si mismo.");
        }

        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<User> optionalUserToFollow = userRepository.findById(userToFollowId);

        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("No se ha encontrado al usuario: " + userId);
        }

        if(optionalUserToFollow.isEmpty()) {
            throw new UserNotFoundException("No se ha encontrado al usuario: " + userToFollowId);
        }

        User user = optionalUser.get();
        User userToUnfollow = optionalUserToFollow.get();
        List<User> userFollowedList = user.getFollowed();
        List<User> userToUnfollowFollowersList = userToUnfollow.getFollowers();

        if(userFollowedList.stream().noneMatch(u -> u.getId().equals(userToFollowId))) {
            throw new BadRequestException("El usuario " + user.getUsername() + " no sigue al usuario " + userToUnfollow.getUsername());
        } else if (userToUnfollowFollowersList.stream().noneMatch(u -> u.getId().equals(userId))) {
            throw new BadRequestException("El usuario " + user.getUsername() + " no se encuentra en la lista de seguidores de: " + userToUnfollow.getUsername());
        }

        userFollowedList.remove(userToUnfollow);
        userToUnfollowFollowersList.remove(user);
        return "¡El usuario " + user.getUsername() + " ha dejado de seguir a " + userToUnfollow.getUsername() + " exitosamente!";

    }

    @Override
    public FollowersListDTO findFollowersList(String userId) {
        Long idLong;

        try {
            idLong = Long.parseLong(userId);
        } catch (NumberFormatException e) {
            throw new BadRequestException("El ID del usuario debe ser un número entero");
        }

        Optional<User> user = userRepository.findById(idLong);
        if(user.isEmpty()){
            throw new UserNotFoundException("El usuario no existe");
        }

        List<User> followersList = user.get().getFollowers();

        List<SimpleUserDTO> followersDtos = followersList.stream()
                .map(follower -> new SimpleUserDTO(follower.getId(), follower.getUsername()))
                .toList();

        FollowersListDTO followersDTO = new FollowersListDTO();

        followersDTO.setUser_id(user.get().getId());
        followersDTO.setUser_name(user.get().getUsername());
        followersDTO.setFollowers(followersDtos);

        return followersDTO;
    
    }

    @Override
    public FollowedListDTO findFollowedList(String userId) {
        Long idLong;

        try {
            idLong = Long.parseLong(userId);
        } catch (NumberFormatException e) {
            throw new BadRequestException("El ID del usuario debe ser un número entero");
        }

        Optional<User> user = userRepository.findById(idLong);
        if(user.isEmpty()){
            throw new UserNotFoundException("El usuario no existe");
        }

        List<User> followedList = user.get().getFollowed();

        List<SimpleUserDTO> followedDtos = followedList.stream()
                .map(follower -> new SimpleUserDTO(follower.getId(), follower.getUsername()))
                .toList();

        FollowedListDTO followedDTO = new FollowedListDTO();

        followedDTO.setUser_id(user.get().getId());
        followedDTO.setUser_name(user.get().getUsername());
        followedDTO.setFollowed(followedDtos);

        return followedDTO;

    }

    public FollowerCountResponse getFollowerCount(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("No se ha encontrado al usuario: " + userId);
        }
        User user = optionalUser.get();
        Long followersCount = (long) user.getFollowers().size();
        FollowerCountResponse followerCountResponse = new FollowerCountResponse();
        followerCountResponse.setUser_id(userId);
        followerCountResponse.setUser_name(user.getUsername());
        followerCountResponse.setFollowers_count(followersCount);
        return followerCountResponse;
    }
}
