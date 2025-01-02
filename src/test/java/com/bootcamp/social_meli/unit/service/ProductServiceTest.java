package com.bootcamp.social_meli.unit.service;

import com.bootcamp.social_meli.dto.response.MostProductsResponseDTO;
import com.bootcamp.social_meli.dto.response.ProductWithPostCountDTO;
import com.bootcamp.social_meli.model.Post;
import com.bootcamp.social_meli.model.Product;
import com.bootcamp.social_meli.repository.IPostRepository;
import com.bootcamp.social_meli.repository.IProductRepository;
import com.bootcamp.social_meli.repository.IUserRepository;
import com.bootcamp.social_meli.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;

class ProductServiceTest {

    @Mock
    IPostRepository postRepository;
    @Mock
    IUserRepository userRepository;
    @Mock
    IProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @Test
    void getAllPostFollowsLastTwoWeeksUnordered() {
    }

    @Test
    void getAllPostsFollowsLastTwoWeeks() {
    }

    @Test
    @DisplayName("Obtiene lista de productos más vendidos usando el parámetro rank")
    public void getMostProductsTestWithRank(){
        // ARR
        String rankParam = "2";
        Product product1 = new Product(1L, "Silla Gamer", "Gamer", "Racer", "Red & Black", "Special Edition");
        Product product2 = new Product(2L, "Teclado Mecánico", "Gamer", "HyperX", "Black", "RGB Backlight");

        Post post1 = new Post(1L, null, LocalDate.now(), product1, null, null, false, null);
        Post post2 = new Post(2L, null, LocalDate.now(), product1, null, null, false, null);
        Post post3 = new Post(3L, null, LocalDate.now(), product2, null, null, false, null);

        when(postRepository.findAll()).thenReturn(Arrays.asList(post1, post2, post3));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product2));

        ProductWithPostCountDTO expectedProduct1 = new ProductWithPostCountDTO(1L, "Silla Gamer", "Gamer", "Racer", "Red & Black", "Special Edition", 2);
        ProductWithPostCountDTO expectedProduct2 = new ProductWithPostCountDTO(2L, "Teclado Mecánico", "Gamer", "HyperX", "Black", "RGB Backlight", 1);
        MostProductsResponseDTO expectedResponse = new MostProductsResponseDTO(Arrays.asList(expectedProduct1, expectedProduct2));

        // ACT
        MostProductsResponseDTO actualResponse = productService.getMostProducts(rankParam);

        // ASS
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Obtiene lista de productos más vendidos sin usar parámetro rank")
    public void getMostProductsTest(){
        // ARR
        Product product1 = new Product(1L, "Silla Gamer", "Gamer", "Racer", "Red & Black", "Special Edition");
        Product product2 = new Product(2L, "Teclado Mecánico", "Gamer", "HyperX", "Black", "RGB Backlight");

        Post post1 = new Post(1L, null, LocalDate.now(), product1, null, null, false, null);
        Post post2 = new Post(2L, null, LocalDate.now(), product1, null, null, false, null);
        Post post3 = new Post(3L, null, LocalDate.now(), product2, null, null, false, null);

        when(postRepository.findAll()).thenReturn(Arrays.asList(post1, post2, post3));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product2));

        ProductWithPostCountDTO expectedProduct1 = new ProductWithPostCountDTO(1L, "Silla Gamer", "Gamer", "Racer", "Red & Black", "Special Edition", 2);
        ProductWithPostCountDTO expectedProduct2 = new ProductWithPostCountDTO(2L, "Teclado Mecánico", "Gamer", "HyperX", "Black", "RGB Backlight", 1);
        MostProductsResponseDTO expectedResponse = new MostProductsResponseDTO(Arrays.asList(expectedProduct1, expectedProduct2));

        // ACT
        MostProductsResponseDTO actualResponse = productService.getMostProducts();

        // ASS
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testGetMostProducts() {
    }

    @Test
    void getAmountOfPromosByUser() {
    }
}