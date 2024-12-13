package com.bootcamp.social_meli.repository.impl;

import com.bootcamp.social_meli.model.Post;
import com.bootcamp.social_meli.repository.IPostRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements IPostRepository {

    private final List<Post> postsList = new ArrayList<>();

    @Override
    public Post create(Post obj) {
        return obj;
    }

    @Override
    public List<Post> findAll() {
        return postsList;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postsList.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    @Override
    public Post delete(Post obj) {
        return null;
    }

    @Override
    public void save(Post obj) {
        postsList.add(obj);
    }

}
