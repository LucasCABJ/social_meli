package com.bootcamp.social_meli.service;

import com.bootcamp.social_meli.dto.SimpleMessageDTO;

public interface IUserService {
    String followUser(Long userId, Long userToFollowId);
}
