package com.codechallenge.api.service;

import com.codechallenge.api.config.AsyncConfig;
import com.codechallenge.api.db.entity.User;
import com.codechallenge.api.db.repo.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @Async(AsyncConfig.TASK_EXECUTOR_SERVICE)
    public CompletableFuture<Page<User>> findAll(final Pageable pageable) {
        return userRepo.findAllBy(pageable);
    }

    @Override
    @Async(AsyncConfig.TASK_EXECUTOR_SERVICE)
    public CompletableFuture<User> findOneById(Long id) {
        return userRepo.findOneById(id);
    }

    @Override
    @Async(AsyncConfig.TASK_EXECUTOR_SERVICE)
    public CompletableFuture<List<User>> findByName(String name) {
        return userRepo.findByNameLike("%" + name + "%");
    }

    @Override
    public void update(User user) {
        userRepo.save(user);
    }

    @Override
    public void delete(User user) {
        userRepo.delete(user);
    }
}
