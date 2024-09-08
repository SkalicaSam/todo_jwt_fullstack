package com.example.springJwt.service;

import com.example.springJwt.exceptions.TaskNotFoundException;
import com.example.springJwt.exceptions.UnauthorizedAccessException;
import com.example.springJwt.model.Task;
import com.example.springJwt.model.User;
import com.example.springJwt.repository.TaskRepository;
import com.example.springJwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getAllTasksByUserToken(String token) {
        String jwtToken = token.substring(7);       // to delete prefix "Bearer " from token
        String username = jwtService.extractUsername(jwtToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Task> tasks =taskRepository.findAllByUserId(user.getId());
        return tasks;
    }

    public Task createTask(String token, Task task) {
        User user = getUserFromToken(token);
        task.setUser(user);
        task.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        if (task.getId() != null) {
            task.setId(null); // to ensure that a new record is created
        }
        return taskRepository.save(task);
    }

    public Task updateTask(Integer id, Task taskDetails, String token) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        task.setName(taskDetails.getName());
        task.setStatus(taskDetails.getStatus());
        task.setCategory(taskDetails.getCategory());
        task.setDescription(taskDetails.getDescription());
        task.setCreatedAt(taskDetails.getCreatedAt());
        User user = getUserFromToken(token);
        task.setUser(user);
        return taskRepository.save(task);
    }

    public Task getTaskByIdAndCheckOwnership(Integer id, String token) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            User userOwnerOfTask = task.getUser();
            User userFromToken = getUserFromToken(token);
            if (userOwnerOfTask.equals(userFromToken)) {
                return task;
            } else {
                throw new UnauthorizedAccessException("Logged user is not owning this task");
            }
        } else {
            throw new TaskNotFoundException("Task with id " + id + " not found");
        }
    }

    public void deleteTaskById(Integer id, String token) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
        User userOwnerOfTask = task.getUser();
        User userFromToken = getUserFromToken(token);
        if (!userOwnerOfTask.equals(userFromToken)) {
            throw new UnauthorizedAccessException("Logged user does not own this task");
        }
        taskRepository.delete(task);
    }

    private User getUserFromToken(String token) {
        String jwtToken = token.substring(7);
        String username = jwtService.extractUsername(jwtToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }

}
