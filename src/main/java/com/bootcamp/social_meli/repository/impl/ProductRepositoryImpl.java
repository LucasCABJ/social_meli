package com.bootcamp.social_meli.repository.impl;

import com.bootcamp.social_meli.model.Product;
import com.bootcamp.social_meli.repository.IProductRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements IProductRepository {

    private final List<Product> productList = new ArrayList<>();

    @Override
    public Product create(Product obj) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return productList;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productList.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    @Override
    public Product delete(Product obj) {
        return null;
    }
}
