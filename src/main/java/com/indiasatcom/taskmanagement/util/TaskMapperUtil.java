package com.indiasatcom.taskmanagement.util;

import java.sql.Date;
import java.time.Instant;

import com.indiasatcom.taskmanagement.dto.TaskRequest;
import com.indiasatcom.taskmanagement.dto.TaskResponse;
import com.indiasatcom.taskmanagement.dto.TaskUpdateRequest;
import com.indiasatcom.taskmanagement.entity.Task;

public class TaskMapperUtil {

    public static TaskResponse mapTaskToTaskResponse(Task task){

        return TaskResponse.builder()
                            .id(task.getId())
                            .title(task.getTitle())
                            .description(task.getDescription())
                            .status(task.getStatus())
                            .dueDate(task.getDueDate())
                            .build();

    }
    
    public static Task mapTaskRequestToTask(TaskRequest request){
        return Task.builder()
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .status(request.getStatus())
                    .dueDate(request.getDueDate())
                    .build();
    }

    public static Task mapTaskUpdateRequestToTask(Task task, TaskUpdateRequest request){
        if(request.getTitle() != null) task.setTitle(request.getTitle());
        if(request.getDescription() != null) task.setDescription(request.getDescription());
        if(request.getStatus() != null) task.setStatus(request.getStatus());
        if(request.getDueDate() != null) task.setDueDate(request.getDueDate());
        return task;
    }
}
