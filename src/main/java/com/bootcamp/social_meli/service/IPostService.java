package com.bootcamp.social_meli.service;

import com.bootcamp.social_meli.dto.PostDTO;
import com.bootcamp.social_meli.dto.PromoPostDTO;
import com.bootcamp.social_meli.dto.response.MostFollowersResponseDTO;
import com.bootcamp.social_meli.dto.response.MostPostsUsersResponseDTO;
import com.bootcamp.social_meli.dto.response.UserPostResponse;

public interface IPostService {
    UserPostResponse createPost(PostDTO postDTO);
    UserPostResponse createPromoPost(PromoPostDTO promoPostDTO);
    MostPostsUsersResponseDTO mostPostsUsers();
    MostPostsUsersResponseDTO mostPostsUsers(Integer rank);
}
