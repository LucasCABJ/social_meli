package com.bootcamp.social_meli.repository.impl;

import com.bootcamp.social_meli.model.Post;
import com.bootcamp.social_meli.model.User;
import com.bootcamp.social_meli.repository.IPostRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements IPostRepository {
    private long currentPostId = 0;

    private final List<Post> postsList = new ArrayList<>();

    @Override
    public Post create(Post obj) {
        currentPostId++;
        obj.setId(currentPostId);
        postsList.add(obj);
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
    public List<Post> findByUserId(Long id) {
        return postsList.stream().filter(p -> p.getCreatorUser().getId().equals(id)).toList();
    }

    @Override
    public List<Post> findByUserIdFilteredByLastTwoWeeks(Long id) {
        return findByUserId(id).stream().filter(post -> post.getCreateDate().isAfter(LocalDate.now().minusWeeks(2))).toList();
    }

    @Override
    public Post delete(Post obj) {
        return null;
    }

    public List<Post> findAmountOfPromosByUserId(User user) {
        return postsList.stream().filter(post -> post.getCreatorUser().equals(user) && post.getHasDiscount()).toList();
    }
    @Override
    public void createBatch(List<Post> posts) {
        postsList.addAll(posts);
    }

    @Override
    public List<Post> getPostsWithProduct(String productName) {
        return postsList.stream().filter(post -> post.hasProductName(productName)).toList();
    }

}
