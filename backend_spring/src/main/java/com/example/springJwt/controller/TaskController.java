package com.example.springJwt.controller;


import com.example.springJwt.exceptions.TaskNotFoundException;
import com.example.springJwt.exceptions.UnauthorizedAccessException;
import com.example.springJwt.model.Task;
import com.example.springJwt.model.User;
import com.example.springJwt.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/tasks")

public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @CrossOrigin(origins = "http://localhost:3000/"
//            , allowedHeaders = {"Authorization", "Content-Type"}
    )
    @GetMapping("/usertasks")
    public List<Task> getAllUsersTasksByUserToken(@RequestHeader("Authorization") String token) {
        return taskService.getAllTasksByUserToken(token);
    }

    @PostMapping
    public Task createTask(@RequestHeader("Authorization") String token, @RequestBody Task task) {
        return taskService.createTask(token, task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Integer id, @RequestBody Task taskDetails, @RequestHeader("Authorization") String token) {
        return taskService.updateTask(id, taskDetails, token);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        try {
            Task task = taskService.getTaskByIdAndCheckOwnership(id, token);
            return ResponseEntity.ok(task);  // HTTP 200 OK s úlohou
        } catch (TaskNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  // HTTP 404 Not Found
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());  // HTTP 403 Forbidden
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        try {
            taskService.deleteTaskById(id, token);
            return ResponseEntity.ok("Task deleted successfully");  // HTTP 200 OK
        } catch (TaskNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  // HTTP 404 Not Found
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());  // HTTP 403 Forbidden
        }
    }



}
