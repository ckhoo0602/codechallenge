package com.codechallenge.api.service;

import com.codechallenge.api.db.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {

    CompletableFuture<Page<User>> findAll(final Pageable pageable);

    CompletableFuture<User> findOneById(final Long id);

    CompletableFuture<List<User>> findByName(final String name);

    void update(User user);

    void delete(User user);
}
