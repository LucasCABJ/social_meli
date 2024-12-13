package com.bootcamp.social_meli.service.impl;

import com.bootcamp.social_meli.dto.PostDTO;
import com.bootcamp.social_meli.dto.response.UserPostResponse;
import com.bootcamp.social_meli.exception.BadRequestException;
import com.bootcamp.social_meli.exception.UserNotFoundException;
import com.bootcamp.social_meli.model.Post;
import com.bootcamp.social_meli.model.User;
import com.bootcamp.social_meli.repository.IPostRepository;
import com.bootcamp.social_meli.repository.IUserRepository;
import com.bootcamp.social_meli.service.IPostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostServiceImpl implements IPostService {
    @Autowired
    private IPostRepository postRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public UserPostResponse createPost(PostDTO postDTO) {
        Optional<User> optionalUser = userRepository.findById(postDTO.getUserId());
        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("No se ha encontrado al usuario: " + postDTO.getUserId());
        }

        if(postRepository.findAll().stream().anyMatch(p->p.getUserId().equals(postDTO.getUserId()) && p.getProduct().getId().equals(postDTO.getProduct().getId()))){
            throw new BadRequestException("Post ya existente para el usuario "+postDTO.getUserId());
        }

        postRepository.save(objectMapper.convertValue(postDTO,Post.class));
        UserPostResponse createPostResponse = new UserPostResponse();
        createPostResponse.setMessage("Post creado exitosamente!");
        createPostResponse.setPostDTO(postDTO);
        return createPostResponse;
    }
}
