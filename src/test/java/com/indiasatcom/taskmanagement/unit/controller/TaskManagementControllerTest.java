package com.indiasatcom.taskmanagement.unit.controller;

import com.indiasatcom.taskmanagement.controller.TaskManagementController;
import com.indiasatcom.taskmanagement.dto.TaskRequest;
import com.indiasatcom.taskmanagement.dto.TaskResponse;
import com.indiasatcom.taskmanagement.dto.TaskUpdateRequest;
import com.indiasatcom.taskmanagement.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TaskManagementControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskManagementController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_shouldReturnCreatedResponse() {
        TaskRequest request = new TaskRequest();
        TaskResponse response = new TaskResponse();
        when(taskService.createTask(any(TaskRequest.class))).thenReturn(response);
        ResponseEntity<TaskResponse> result = controller.createTask(request);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void getAllTasks_shouldReturnList() {
        TaskResponse response = new TaskResponse();
        when(taskService.getAllTasks(0, 10, null)).thenReturn(Arrays.asList(response));
        ResponseEntity<List<TaskResponse>> result = controller.getAllTasks(0, 10, null);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void getTaskById_shouldReturnTask() {
        TaskResponse response = new TaskResponse();
        when(taskService.getTask("1")).thenReturn(response);
        ResponseEntity<TaskResponse> result = controller.getAllTasks("1");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void updateTask_shouldReturnUpdatedTask() {
        TaskUpdateRequest updateRequest = new TaskUpdateRequest();
        TaskResponse response = new TaskResponse();
        when(taskService.updateTask(eq("1"), any(TaskUpdateRequest.class))).thenReturn(response);
        ResponseEntity<TaskResponse> result = controller.updateTask("1", updateRequest);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void deleteTask_shouldReturnNoContent() {
        doNothing().when(taskService).deleteTask("1");
        ResponseEntity<Void> result = controller.deleteTask("1");
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertNull(result.getBody());
    }
}
