package com.bootcamp.social_meli.service.impl;

import com.bootcamp.social_meli.dto.PostDTO;
import com.bootcamp.social_meli.dto.PromoPostDTO;
import com.bootcamp.social_meli.dto.response.MostPostsUsersResponseDTO;
import com.bootcamp.social_meli.dto.response.SimpleUserWithPostsCountDTO;
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

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements IPostService {
    private IPostRepository postRepository;
    private IUserRepository userRepository;
    private IProductRepository productRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public PostServiceImpl(IPostRepository postRepository, IUserRepository userRepository, IProductRepository productRepository, ObjectMapper objectMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

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

    @Override
    public MostPostsUsersResponseDTO mostPostsUsers() {
        return mostPostsUsers(5);
    }

    @Override
    public MostPostsUsersResponseDTO mostPostsUsers(Integer rank) {
        if(rank <= 0) {
            throw new BadRequestException("'rank' no puede ser <= 0");
        }

        HashMap<Long, SimpleUserWithPostsCountDTO> usersWithProducts = new HashMap<>();

        postRepository.findAll().forEach(p -> {
            // 1. Obtengo el creador del posteo
            Long postCreatorId = p.getCreatorUser().getId();
            String postCreatorUsername = p.getCreatorUser().getUsername();
            // 2. Me fijo si ya lo incluí en el HashMap "usersWithProducts"
            if(usersWithProducts.containsKey(postCreatorId)) {
                // 3a. Lo obtengo, incrementó en 1 y actualizo en el mapa
                SimpleUserWithPostsCountDTO user = usersWithProducts.get(postCreatorId);
                user.setPosts_amount(user.getPosts_amount() + 1);
                usersWithProducts.replace(postCreatorId, user);
            } else {
                // 3b. Lo agregó por primera vez y setteo en 1
                usersWithProducts.put(postCreatorId,
                        new SimpleUserWithPostsCountDTO(postCreatorId,
                                postCreatorUsername,
                                1));
            }
        });

        List<SimpleUserWithPostsCountDTO> result;
        if(usersWithProducts.size() < rank) {
            result = usersWithProducts.values().stream().toList();
        } else {
            result = usersWithProducts.values().stream().toList().subList(0, rank);
        }

        return new MostPostsUsersResponseDTO(result);
    }
}
