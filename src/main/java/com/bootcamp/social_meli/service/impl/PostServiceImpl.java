package com.bootcamp.social_meli.service.impl;

import com.bootcamp.social_meli.dto.PostDTO;
import com.bootcamp.social_meli.dto.PromoPostDTO;
import com.bootcamp.social_meli.dto.response.PostsWithProductDTO;
import com.bootcamp.social_meli.dto.response.UserPostResponse;
import com.bootcamp.social_meli.exception.BadRequestException;
import com.bootcamp.social_meli.exception.NotFoundException;
import com.bootcamp.social_meli.model.Post;
import com.bootcamp.social_meli.model.User;
import com.bootcamp.social_meli.repository.IPostRepository;
import com.bootcamp.social_meli.repository.IProductRepository;
import com.bootcamp.social_meli.repository.IUserRepository;
import com.bootcamp.social_meli.service.IPostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements IPostService {
    @Autowired
    private IPostRepository postRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public UserPostResponse createPost(PostDTO postDTO) {
        Long userId = postDTO.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("No se ha encontrado al usuario: " + userId));

        if(postRepository.findAll().stream().anyMatch(p->p.getCreatorUser().getId().equals(postDTO.getUserId()) && p.getProduct().getId().equals(postDTO.getProduct().getProduct_id()))){
            throw new BadRequestException("Post ya existente para el usuario "+postDTO.getUserId());
        }

        Post post = objectMapper.convertValue(postDTO,Post.class);
        post.setCreatorUser(user);

        if(productRepository.findAll().stream().noneMatch(product -> product.getId().equals(post.getProduct().getId()))){
            productRepository.create(post.getProduct());
        }

        postRepository.create(post);

        return createUserResponse(post);
    }

    @Override
    public UserPostResponse createPromoPost(PromoPostDTO promoPostDTO) {
        Long userId = promoPostDTO.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("No se ha encontrado al usuario: " + userId));

        if(postRepository.findAll().stream().anyMatch(p->p.getCreatorUser().getId().equals(userId) && p.getProduct().getId().equals(promoPostDTO.getProduct().getProduct_id()))){
            throw new BadRequestException("Post ya existente para el usuario "+userId);
        }

        Post post = objectMapper.convertValue(promoPostDTO,Post.class);
        post.setCreatorUser(user);

        if(productRepository.findAll().stream().noneMatch(product -> product.getId().equals(post.getProduct().getId()))){
            productRepository.create(post.getProduct());
        }

        postRepository.create(post);

        return createUserResponse(post);
    }

    @Override
    public PostsWithProductDTO getPostsWithProduct(String productName) {
        return new PostsWithProductDTO(productName, postRepository.getPostsWithProduct(productName));
    }

    public UserPostResponse createUserResponse(Post post){
        UserPostResponse userPostResponse = new UserPostResponse();
        userPostResponse.setMessage("Post creado exitosamente!");
        userPostResponse.setUser_id(post.getCreatorUser().getId());
        userPostResponse.setDate(post.getCreateDate());
        userPostResponse.setProduct(post.getProduct());
        userPostResponse.setCategory(post.getCategory());
        userPostResponse.setPrice(post.getPrice());
        if(post.getHasDiscount() != null)
            userPostResponse.setHas_promo(post.getHasDiscount());
        if(post.getDiscountPercentage() != null)
            userPostResponse.setDiscount(post.getDiscountPercentage());

        return userPostResponse;
    }
}
