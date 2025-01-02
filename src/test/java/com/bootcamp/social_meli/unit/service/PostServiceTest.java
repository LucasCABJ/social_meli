package com.bootcamp.social_meli.unit.service;

import com.bootcamp.social_meli.dto.response.PostsWithProductResponseDTO;
import com.bootcamp.social_meli.model.Post;
import com.bootcamp.social_meli.model.Product;
import com.bootcamp.social_meli.repository.impl.PostRepositoryImpl;
import com.bootcamp.social_meli.service.impl.PostServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepositoryImpl postRepository;
    @InjectMocks
    PostServiceImpl postService;

    @Test
    void createPost() {
    }

    @Test
    void createPromoPost() {
    }

    @Test
    void updatePromoPost() {
    }

    @Test
    @DisplayName("La cantidad de posts de un producto deberia ser cero si no hay posts con ese producto")
    void getPostsWithProductReturnsZero() {
        Integer expectedAmount = 0;
        String productName = "Taza";
        when(postRepository.getPostsWithProduct(Mockito.anyString())).thenReturn(List.of());

        PostsWithProductResponseDTO response = postService.getPostsWithProduct(productName);

        Assertions.assertEquals(expectedAmount, response.getPosts().size());
    }

    @Test
    @DisplayName("La cantidad de posts de un producto deberia ser dos si hay dos posts con ese producto")
    void getPostsWithProduct() {
        Integer expectedAmount = 2;
        String productName = "Taza";
        Post post1 = new Post();
        Post post2 = new Post();
        when(postRepository.getPostsWithProduct(Mockito.anyString())).thenReturn(List.of(post1, post2));

        PostsWithProductResponseDTO response = postService.getPostsWithProduct(productName);

        Assertions.assertEquals(expectedAmount, response.getPosts().size());
    }

    @Test
    @DisplayName("Los posts que contienen un producto, son del tipo de producto deseado")
    void getPostsWithProductReturnsTheProductDesired(){
        String productName = "Taza";
        Product product = new Product();
        product.setName("Taza");
        Post post = new Post();
        post.setProduct(product);
        when(postRepository.getPostsWithProduct(Mockito.anyString())).thenReturn(List.of(post));

        PostsWithProductResponseDTO response = postService.getPostsWithProduct(productName);
        Post postTaza = response.getPosts().get(0);

        Assertions.assertEquals(productName, response.getName());
        Assertions.assertTrue(postTaza.productNameContains(productName));
    }

    @Test
    void createUserResponse() {
    }

    @Test
    void mostPostsUsers() {
    }

    @Test
    void testMostPostsUsers() {
    }

    @Test
    void getPostsByPriceRange() {
    }

    @Test
    void parseStringToDouble() {
    }

    @Test
    void convertToPostDTO() {
    }
}