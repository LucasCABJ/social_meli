package com.bootcamp.social_meli.unit.service;

import com.bootcamp.social_meli.dto.response.MostProductsResponseDTO;
import com.bootcamp.social_meli.dto.response.ProductWithPostCountDTO;
import com.bootcamp.social_meli.exception.BadRequestException;
import com.bootcamp.social_meli.exception.NotFoundException;
import com.bootcamp.social_meli.model.Post;
import com.bootcamp.social_meli.model.Product;
import com.bootcamp.social_meli.model.User;
import com.bootcamp.social_meli.repository.IPostRepository;
import com.bootcamp.social_meli.repository.IProductRepository;
import com.bootcamp.social_meli.repository.IUserRepository;
import com.bootcamp.social_meli.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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
        User user1 = new User(1L, "FrancoCol43", "Franco", "Colapinto", new ArrayList<>(), new ArrayList<>());
        User user2 = new User(2L, "MartinG23", "Martín", "Gómez", new ArrayList<>(), new ArrayList<>());

        Product product1 = new Product(1L, "Silla Gamer", "Gamer", "Racer", "Red & Black", "Special Edition");
        Product product2 = new Product(2L, "Teclado Mecánico", "Gamer", "HyperX", "Black", "RGB Backlight");

        // Convertir la fecha de String a LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date1 = LocalDate.parse("30-11-2024", formatter);
        LocalDate date2 = LocalDate.parse("12-12-2024", formatter);
        LocalDate date3 = LocalDate.parse("02-12-2024", formatter);

        // Crea instancias de Post
        Post post1 = new Post(1L, user1, date1, product1, 100, 1500.50, true, 0.25);
        Post post2 = new Post(2L, user1, date2, product2, 112, 120.00, false, 0.0);
        Post post3 = new Post(3L, user2, date3, product2, 102, 80.75, true, 0.15);

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
        User user1 = new User(1L, "FrancoCol43", "Franco", "Colapinto", new ArrayList<>(), new ArrayList<>());
        User user2 = new User(2L, "MartinG23", "Martín", "Gómez", new ArrayList<>(), new ArrayList<>());

        Product product1 = new Product(1L, "Silla Gamer", "Gamer", "Racer", "Red & Black", "Special Edition");
        Product product2 = new Product(2L, "Teclado Mecánico", "Gamer", "HyperX", "Black", "RGB Backlight");

        // Convertir la fecha de String a LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date1 = LocalDate.parse("30-11-2024", formatter);
        LocalDate date2 = LocalDate.parse("12-12-2024", formatter);
        LocalDate date3 = LocalDate.parse("02-12-2024", formatter);

        // Crea instancias de Post
        Post post1 = new Post(1L, user1, date1, product1, 100, 1500.50, true, 0.25);
        Post post2 = new Post(2L, user1, date2, product2, 112, 120.00, false, 0.0);
        Post post3 = new Post(3L, user2, date3, product2, 102, 80.75, true, 0.15);

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
    @DisplayName("Arroja BadRequest si rank no es un número")
    public void getMostProductsTestThrowsExceptionIfBadRequest() {
        // ARR
        String rankParam = "not_a_number";

        // ACT & ASSERT
        BadRequestException exception = Assertions.assertThrows(BadRequestException.class, () -> {
            productService.getMostProducts(rankParam);
        });

        Assertions.assertEquals("El rank debe ser un valor numerico.", exception.getMessage());
    }

    @Test
    void testGetMostProducts() {
    }

    @Test
    void getAmountOfPromosByUser() {
    }
}