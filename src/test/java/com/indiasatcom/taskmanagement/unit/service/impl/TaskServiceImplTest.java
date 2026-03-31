package com.indiasatcom.taskmanagement.unit.service.impl;

import com.indiasatcom.taskmanagement.dto.TaskRequest;
import com.indiasatcom.taskmanagement.dto.TaskResponse;
import com.indiasatcom.taskmanagement.dto.TaskUpdateRequest;
import com.indiasatcom.taskmanagement.entity.Task;
import com.indiasatcom.taskmanagement.enums.TaskStatus;
import com.indiasatcom.taskmanagement.exceptions.ResourceNotFoundException;
import com.indiasatcom.taskmanagement.repository.TaskRepository;
import com.indiasatcom.taskmanagement.service.impl.TaskServiceImpl;
import com.indiasatcom.taskmanagement.util.TaskMapperUtil;

import jakarta.validation.ValidationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_shouldSetDefaultStatusAndSave() {
        TaskRequest request = new TaskRequest();
        request.setTitle("Test");
        Task mappedTask = new Task();
        mappedTask.setStatus(TaskStatus.PENDING);
        when(taskRepository.save(any(Task.class))).thenReturn(mappedTask);
        TaskResponse response = taskService.createTask(request);
        assertNotNull(response);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void getAllTasks_shouldReturnList() {
        Task task = new Task();
        when(taskRepository.findAll(any(Pageable.class))).thenReturn(new org.springframework.data.domain.PageImpl<>(Arrays.asList(task)));
        List<TaskResponse> result = taskService.getAllTasks(0, 10, null);
        assertEquals(1, result.size());
    }

    @Test
    void getTask_shouldReturnTaskResponse() {
        Task task = new Task();
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        TaskResponse response = taskService.getTask("1");
        assertNotNull(response);
    }

    @Test
    void getTask_shouldThrowResourceNotFoundException() {
        when(taskRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> taskService.getTask("1"));
    }

    @Test
    void updateTask_shouldUpdateAndReturnResponse() {
        Task task = new Task();
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        TaskUpdateRequest updateRequest = new TaskUpdateRequest();
        updateRequest.setDueDate(new java.sql.Date(Date.from(Instant.now().plusSeconds(3600)).getTime()));
        TaskResponse response = taskService.updateTask("1", updateRequest);
        assertNotNull(response);
    }

    @Test
    void updateTask_shouldThrowResourceNotFoundException() {
        when(taskRepository.findById("1")).thenReturn(Optional.empty());
        TaskUpdateRequest updateRequest = new TaskUpdateRequest();
        assertThrows(ResourceNotFoundException.class, () -> taskService.updateTask("1", updateRequest));
    }

    @Test
    void updateTask_shouldThrowValidationException() {
        Task task = new Task();
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        TaskUpdateRequest updateRequest = new TaskUpdateRequest();
        updateRequest.setDueDate(new java.sql.Date(Date.from(Instant.now().minusSeconds(3600)).getTime()));
        assertThrows(ValidationException.class, () -> taskService.updateTask("1", updateRequest));
    }

    @Test
    void deleteTask_shouldDeleteIfExists() {
        Task task = new Task();
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        assertDoesNotThrow(() -> taskService.deleteTask("1"));
        verify(taskRepository).deleteById("1");
    }

    @Test
    void deleteTask_shouldThrowResourceNotFoundException() {
        when(taskRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> taskService.deleteTask("1"));
    }
}
