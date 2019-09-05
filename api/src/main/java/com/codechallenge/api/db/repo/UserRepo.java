package com.codechallenge.api.db.repo;

import com.codechallenge.api.config.AsyncConfig;
import com.codechallenge.api.db.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Async(AsyncConfig.TASK_EXECUTOR_REPOSITORY)
    CompletableFuture<Page<User>> findAllBy(final Pageable pageable);

    @Async(AsyncConfig.TASK_EXECUTOR_REPOSITORY)
    CompletableFuture<User> findOneById(final Long id);

    @Async(AsyncConfig.TASK_EXECUTOR_REPOSITORY)
    CompletableFuture<List<User>> findByNameLike(final String name);
}
