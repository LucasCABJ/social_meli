package com.bootcamp.social_meli.service.impl;

import com.bootcamp.social_meli.exception.UserNotFoundException;
import com.bootcamp.social_meli.model.Post;
import com.bootcamp.social_meli.model.User;
import com.bootcamp.social_meli.repository.IPostRepository;
import com.bootcamp.social_meli.repository.IUserRepository;
import com.bootcamp.social_meli.service.IProductService;
import com.bootcamp.social_meli.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IPostRepository postRepository;

    @Override
    public Integer getAmountOfPromosByUser(Long user_id) {
        Optional<User> user = userRepository.findById(user_id);
        if (user.isEmpty()){
            throw new UserNotFoundException("Usuario no encontrado");
        }
        List<Post> posts = postRepository.findPostsByUserId(user.get());

        posts = posts.stream().filter(post -> post.getDiscountPercentage() != null).toList();


        return posts.size();
    }
}
