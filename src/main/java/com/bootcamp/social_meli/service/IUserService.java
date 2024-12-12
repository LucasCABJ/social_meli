package com.bootcamp.social_meli.service;

import com.bootcamp.social_meli.dto.UserDTO;
import com.bootcamp.social_meli.dto.SimpleMessageDTO;
import java.util.List;

public interface IUserService {
    List<UserDTO> findAll();
    String followUser(Long userId, Long userToFollowId);
}
