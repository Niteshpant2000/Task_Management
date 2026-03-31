package com.indiasatcom.taskmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.indiasatcom.taskmanagement.dto.TaskRequest;
import com.indiasatcom.taskmanagement.dto.TaskResponse;
import com.indiasatcom.taskmanagement.dto.TaskUpdateRequest;
import com.indiasatcom.taskmanagement.enums.TaskStatus;
import com.indiasatcom.taskmanagement.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskManagementController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest task){
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(taskService.createTask(task));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                         @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                                         @RequestParam(value = "status", required = false) TaskStatus status) {

        return ResponseEntity.ok(taskService.getAllTasks(page, size, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getAllTasks(@PathVariable String id){
        return ResponseEntity.ok(taskService.getTask(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable String id, @RequestBody(required = false) TaskUpdateRequest task){
        return ResponseEntity.ok(taskService.updateTask(id, task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id){
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
