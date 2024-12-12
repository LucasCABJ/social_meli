package com.bootcamp.social_meli.service.impl;

import com.bootcamp.social_meli.dto.UserDTO;
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
}
