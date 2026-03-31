package com.indiasatcom.taskmanagement.service.impl;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.indiasatcom.taskmanagement.dto.TaskRequest;
import com.indiasatcom.taskmanagement.dto.TaskResponse;
import com.indiasatcom.taskmanagement.dto.TaskUpdateRequest;
import com.indiasatcom.taskmanagement.entity.Task;
import com.indiasatcom.taskmanagement.enums.Constants;
import com.indiasatcom.taskmanagement.enums.TaskStatus;
import com.indiasatcom.taskmanagement.exceptions.ResourceNotFoundException;
import com.indiasatcom.taskmanagement.repository.TaskRepository;
import com.indiasatcom.taskmanagement.service.TaskService;
import com.indiasatcom.taskmanagement.util.TaskMapperUtil;

import jakarta.validation.ValidationException;
import lombok.SneakyThrows;

@Service
public class TaskServiceImpl implements TaskService {
    
    @Autowired
    private TaskRepository taskRepository;

    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest request) {
        if(request.getStatus()==null){
            request.setStatus(TaskStatus.PENDING);
        }
        Task task = taskRepository.save(TaskMapperUtil.mapTaskRequestToTask(request));
        return TaskMapperUtil.mapTaskToTaskResponse(task);
    }

    @Override
    @Transactional
    public List<TaskResponse> getAllTasks(int page, int size ,TaskStatus status) {
       PageRequest pageRequest = PageRequest.of(page, size);
       if(status == null){
        return taskRepository.findAll(pageRequest)
                            .stream()
                            .map(TaskMapperUtil::mapTaskToTaskResponse)
                            .toList();
       }

       return taskRepository.findByStatusOrderByDueDate(pageRequest, status)
                            .stream()
                            .map(TaskMapperUtil::mapTaskToTaskResponse)
                            .toList();
        
    }

    @Override
    @SneakyThrows
    @Transactional
    public TaskResponse getTask(String id) {
        Task task = taskRepository.findById(id).orElse(null);
        if(task == null) throw new ResourceNotFoundException(Constants.NOT_FOUND.toString() + id);
        return TaskMapperUtil.mapTaskToTaskResponse(task);
    }

    @Override
    @SneakyThrows
    @Transactional
    public TaskResponse updateTask(String id, TaskUpdateRequest request) {
        if(request!=null && request.getDueDate() != null 
                         && request.getDueDate()
                                    .before(Date.from(Instant.now()))){
            throw new ValidationException("Due Date cannot be less than current date");
        }
        Task task = taskRepository.findById(id).orElse(null);
        if(task != null){
            task = TaskMapperUtil.mapTaskUpdateRequestToTask(task, request);
            taskRepository.save(task);
            return TaskMapperUtil.mapTaskToTaskResponse(task);
        }else{
            throw new ResourceNotFoundException(Constants.NOT_FOUND.toString() + id);
        }
    }

    @Override
    @SneakyThrows
    @Transactional
    public void deleteTask(String id) {
        Task task = taskRepository.findById(id).orElse(null);
        if(task != null){
            taskRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException(Constants.NOT_FOUND.toString() + id);
        }
    }
    
}
