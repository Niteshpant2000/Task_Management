package com.indiasatcom.taskmanagement.dto;

import java.sql.Date;

import com.indiasatcom.taskmanagement.enums.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskResponse {
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private Date dueDate;
}
