package com.indiasatcom.taskmanagement.entity;

import java.sql.Date;

import com.indiasatcom.taskmanagement.enums.TaskStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @NotNull
    @NotBlank
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @NotNull
    @Column(name = "due_date")
    private Date dueDate;

}
