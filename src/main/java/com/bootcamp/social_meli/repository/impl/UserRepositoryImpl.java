package com.bootcamp.social_meli.repository.impl;

import com.bootcamp.social_meli.model.User;
import com.bootcamp.social_meli.repository.IUserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    @Autowired
    private ObjectMapper objectMapper;
    private List<User> usersList = new ArrayList<>();

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return usersList;
    }

    @Override
    public Optional<User> findById(Long id) {
        return usersList.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    @Override
    public User delete(User user) {
        return null;
    }

    @Override
    public void save(User obj) {

    }

    @PostConstruct
    private void loadData() {
        String DATA_FILE = "src/main/resources/users.json";
        try {
            usersList = objectMapper.readValue(new File(DATA_FILE), new TypeReference<List<User>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
