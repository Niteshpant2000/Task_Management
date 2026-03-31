package com.indiasatcom.taskmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import com.indiasatcom.taskmanagement.entity.Task;
import com.indiasatcom.taskmanagement.enums.TaskStatus;

import jakarta.persistence.LockModeType;

@Repository
public interface TaskRepository extends JpaRepository<Task,String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Task save(Task task);

    @Lock(LockModeType.PESSIMISTIC_READ)
    public List<Task> findByStatusOrderByDueDate(PageRequest page, TaskStatus status);

    @Lock(LockModeType.PESSIMISTIC_READ)
    public Optional<Task> findById(String id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void deleteById(String id);


}
