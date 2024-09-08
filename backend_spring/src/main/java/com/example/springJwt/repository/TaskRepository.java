package com.example.springJwt.repository;

import com.example.springJwt.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByUserId(Integer userId);

}

