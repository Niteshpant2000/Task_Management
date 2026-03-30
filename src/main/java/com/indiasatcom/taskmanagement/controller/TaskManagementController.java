package com.indiasatcom.taskmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indiasatcom.taskmanagement.enums.TaskStatus;
import com.indiasatcom.taskmanagement.dao.TaskRequest;
import com.indiasatcom.taskmanagement.dao.TaskResponse;

import ch.qos.logback.core.model.processor.PhaseIndicator;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/tasks")
public class TaskManagementController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest task){
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(taskService.createTask(task));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(@RequestParam(value = "status", required =false) TaskStatus status){

        return ResponseEntity.ok(taskstaskService.getAllTasks(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getAllTasks(@PathVariable String id){
        return ResponseEntity.ok(taskstaskService.getTask(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable String id, @RequestBody TaskRequest task){
        return ResponseEntity.ok(taskService.updateTask(id, task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable String id){
     
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(taskService.deleteTask(id));
    }
}
