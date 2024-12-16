package com.bootcamp.social_meli.repository;

import com.bootcamp.social_meli.model.Post;
import java.util.List;

public interface IPostRepository extends ICrudRepository<Post, Long> {
    List<Post> findByUserId(Long id);
    List<Post> findByUserIdFilteredByLastTwoWeeks(Long id);
    void createBatch(List<Post> posts);
}
