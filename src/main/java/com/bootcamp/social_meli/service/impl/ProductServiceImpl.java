package com.bootcamp.social_meli.service.impl;

import com.bootcamp.social_meli.dto.response.*;
import com.bootcamp.social_meli.exception.NotFoundException;
import com.bootcamp.social_meli.model.Post;
import com.bootcamp.social_meli.model.User;
import com.bootcamp.social_meli.repository.IPostRepository;
import com.bootcamp.social_meli.repository.IUserRepository;
import com.bootcamp.social_meli.dto.response.AmountOfPromosDTO;
import com.bootcamp.social_meli.service.IProductService;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Comparator;

@Service
public class ProductServiceImpl implements IProductService {
    private final IUserRepository userRepository;
    private final IPostRepository postRepository ;

    public ProductServiceImpl(IUserRepository userRepository, IPostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }
    @Override
    public PostsFromFollowsDTO getAllPostFollowsLastTwoWeeksUnordered(Long userId) {
        List<User> followedList = userRepository.findFollowsByUserId(userId);
        if (followedList == null) throw new NotFoundException("The user with id: " + userId + " does not follow anyone");

        List<PostNoDiscountDTO> postsNoDiscountFromFollowsDTOSList = new ArrayList<>();

        for (User user : followedList) {
            List<PostNoDiscountDTO> postNoDiscountDTOList;

            List<Post> lastTwoWeeksPosts = postRepository.findByUserIdFilteredByLastTwoWeeks(user.getId());

            postNoDiscountDTOList = lastTwoWeeksPosts.stream().map(post -> new PostNoDiscountDTO(post.getCreatorUser().getId(), post.getId(), post.getCreateDate(), new ProductDTO(
                    post.getProduct().getId(),
                    post.getProduct().getName(),
                    post.getProduct().getType(),
                    post.getProduct().getBrand(),
                    post.getProduct().getColor(),
                    post.getProduct().getNotes()
            ), post.getCategory(), post.getPrice())).toList();

            postsNoDiscountFromFollowsDTOSList.addAll(postNoDiscountDTOList);
        }

        PostsFromFollowsDTO postsFromFollowsDTO = new PostsFromFollowsDTO(userId, postsNoDiscountFromFollowsDTOSList);

        return postsFromFollowsDTO;
    }
    @Override
    public PostsFromFollowsDTO getAllPostsFollowsLastTwoWeeks(Long userId, String order) {
        PostsFromFollowsDTO postsFromFollowsDTOSList = getAllPostFollowsLastTwoWeeksUnordered(userId);
        List<PostNoDiscountDTO> posts = postsFromFollowsDTOSList.getPosts();

        if (order != null && !StringUtils.isBlank(order)) {
            Comparator<PostNoDiscountDTO> comparator = Comparator.comparing(PostNoDiscountDTO::getCreateDate);

            posts = postsFromFollowsDTOSList.getPosts().stream()
                    .sorted("date_desc".equals(order) ? comparator.reversed() : comparator)
                    .toList();
        }

        return new PostsFromFollowsDTO(userId, posts);
    }



    @Override
    public AmountOfPromosDTO getAmountOfPromosByUser(Long user_id) {
        Optional<User> user = userRepository.findById(user_id);
        if (user.isEmpty()){
            throw new NotFoundException("Usuario no encontrado");
        }
        List<Post> amountOfPromos = postRepository.findAmountOfPromosByUserId(user.get());
        AmountOfPromosDTO amountOfPromosDTO = new AmountOfPromosDTO(amountOfPromos.size());
        amountOfPromosDTO.setUser_id(user.get().getId());
        amountOfPromosDTO.setUser_name(user.get().getUsername());

        return amountOfPromosDTO;
    }
}
