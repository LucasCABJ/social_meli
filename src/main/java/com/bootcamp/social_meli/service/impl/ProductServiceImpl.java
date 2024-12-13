package com.bootcamp.social_meli.service.impl;


import com.bootcamp.social_meli.dto.response.*;
import com.bootcamp.social_meli.exception.UserNotFoundException;
import com.bootcamp.social_meli.model.Post;
import com.bootcamp.social_meli.model.User;
import com.bootcamp.social_meli.repository.IPostRepository;
import com.bootcamp.social_meli.repository.IUserRepository;
import com.bootcamp.social_meli.service.IProductService;
import com.bootcamp.social_meli.service.IUserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
@Service
public class ProductServiceImpl implements IProductService {
    private final IUserRepository userRepository;
    private final IPostRepository postRepository ;
    private final IUserService userService;

    public ProductServiceImpl(IUserRepository userRepository, UserServiceImpl userService, IPostRepository postRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.postRepository = postRepository;
    }
    @Override
    public List<PostsFromFollowsDTO> getAllPostFollowsLastTwoWeeks(Long userId) {
        List<User> followedList = userService.findFollowedUserList(userId);
        if (followedList == null) throw new UserNotFoundException("The user with id: " + userId + " does not follow anyone");

        List<PostsFromFollowsDTO> postsFromFollowsDTOSList = new ArrayList<>();

        for (User user : followedList) {
            List<PostNoDiscountDTO> postNoDiscountDTOList = new ArrayList<>();

            // encuentro los posteos de cada usuario, los filtro para que sean de las ultimas dos semanas
            List<Post> postsByUserList = postRepository.findByUserId(user.getId());
            List<Post> lastTwoWeeksPosts = postsByUserList.stream().filter(post -> post.getCreateDate().isAfter(LocalDate.now().minusWeeks(2))).toList();

            // acá ver qué onda si no tiene posts en las ultimas dos semanas, lo incluyo con una lista vacía o no lo incluyo?

            postNoDiscountDTOList = lastTwoWeeksPosts.stream().map(post -> new PostNoDiscountDTO(post.getCreatorUser().getId(), post.getId(), post.getCreateDate(), new ProductDTO(
                    post.getProduct().getId(),
                    post.getProduct().getName(),
                    post.getProduct().getType(),
                    post.getProduct().getBrand(),
                    post.getProduct().getColor(),
                    post.getProduct().getNotes()
            ), post.getCategory(), post.getPrice())).toList();

            PostsFromFollowsDTO postsFromFollowsDTO = new PostsFromFollowsDTO(user.getId(), postNoDiscountDTOList);

            postsFromFollowsDTOSList.add(postsFromFollowsDTO);

        }

        return postsFromFollowsDTOSList;
    }
    @Override
    public PostsFromFollowsDTO getAllPostsFollowsLastTwoWeeks(Long userId, String order) {
        List<PostsFromFollowsDTO> postsFromFollowsDTOSList = getAllPostFollowsLastTwoWeeks(userId);

        Comparator<PostNoDiscountDTO> comparator = Comparator.comparing(PostNoDiscountDTO::getCreateDate);

        List<PostNoDiscountDTO> posts = postsFromFollowsDTOSList.stream()
                .flatMap(dto -> dto.getPosts().stream())
                .sorted("date_desc".equals(order) ? comparator.reversed() : comparator)
                .toList();

        return new PostsFromFollowsDTO(userId, posts);
    }

}
