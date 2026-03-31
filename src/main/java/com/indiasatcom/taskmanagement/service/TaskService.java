package com.indiasatcom.taskmanagement.service;

import java.util.List;

import com.indiasatcom.taskmanagement.dto.TaskRequest;
import com.indiasatcom.taskmanagement.dto.TaskResponse;
import com.indiasatcom.taskmanagement.dto.TaskUpdateRequest;
import com.indiasatcom.taskmanagement.enums.TaskStatus;


public interface TaskService {
    public TaskResponse createTask(TaskRequest request);

    public List<TaskResponse> getAllTasks(int page, int size, TaskStatus status);

    public TaskResponse getTask(String id);

    public TaskResponse updateTask(String id, TaskUpdateRequest request);

    public void deleteTask(String id);

}
