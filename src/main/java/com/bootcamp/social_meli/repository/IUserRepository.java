package com.bootcamp.social_meli.repository;

import com.bootcamp.social_meli.model.User;

import java.util.List;

public interface IUserRepository extends ICrudRepository<User, Long> {
    List<User> getFollowersById(Long id);
    List<User> getFollowedById(Long id);
}
