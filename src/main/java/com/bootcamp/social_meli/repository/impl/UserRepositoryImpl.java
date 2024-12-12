package com.bootcamp.social_meli.repository.impl;

import com.bootcamp.social_meli.model.User;
import com.bootcamp.social_meli.repository.IUserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    private final List<User> usersList = new ArrayList<>();

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
}
