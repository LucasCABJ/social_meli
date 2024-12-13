package com.bootcamp.social_meli.service;

import com.bootcamp.social_meli.dto.PostDTO;
import com.bootcamp.social_meli.dto.response.UserPostResponse;

public interface IPostService {
    UserPostResponse createPost(PostDTO postDTO);
}
