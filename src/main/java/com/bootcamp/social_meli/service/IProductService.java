package com.bootcamp.social_meli.service;

import com.bootcamp.social_meli.dto.response.PostsFromFollowsDTO;

import java.util.List;

public interface IProductService {
    PostsFromFollowsDTO getAllPostFollowsLastTwoWeeksUnordered(Long userId);
    PostsFromFollowsDTO getAllPostsFollowsLastTwoWeeks(Long userId, String order);
}
