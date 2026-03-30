package com.indiasatcom.taskmanagement.dao;

import java.sql.Date;

import com.indiasatcom.taskmanagement.enums.TaskStatus;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskResponse {
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private Date due_date;
}
