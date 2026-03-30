package com.indiasatcom.taskmanagement.dao;

import java.sql.Date;

import com.indiasatcom.taskmanagement.enums.TaskStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskRequest {
    @NotNull
    @NotBlank
    private String title;
    private String description;
    private TaskStatus status;
    private Date due_date;
}
