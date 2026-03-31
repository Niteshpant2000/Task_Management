package com.indiasatcom.taskmanagement.dto;

import java.sql.Date;

import com.indiasatcom.taskmanagement.enums.TaskStatus;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class TaskUpdateRequest {
    @Nullable
    private String title;
    @Nullable
    private String description;
    @Nullable
    private TaskStatus status;
    @Nullable
    private Date dueDate;
    
}
