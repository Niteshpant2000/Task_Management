package com.indiasatcom.taskmanagement.dto;

import java.sql.Date;

import com.indiasatcom.taskmanagement.enums.TaskStatus;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskRequest {
    @NotNull(message = "Title cannot be null")
    private String title;
    private String description;
    private TaskStatus status;
    @NotNull(message = "Due date cannot be null")
    @Future(message = "Due date must be in the future")
    private Date dueDate;
}
