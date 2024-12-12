package com.bootcamp.social_meli.service;

import com.bootcamp.social_meli.dto.UserDTO;

import java.util.List;

public interface IUserService {
    List<UserDTO> findAll();
}
