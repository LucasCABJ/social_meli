package com.bootcamp.social_meli.repository;

import com.bootcamp.social_meli.model.Post;
import com.bootcamp.social_meli.model.User;

import java.util.List;

public interface IPostRepository extends ICrudRepository<Post, Long> {
    List<Post> findAmountOfPromosByUserId(User user);
}
