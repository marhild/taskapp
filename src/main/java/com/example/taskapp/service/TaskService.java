package com.example.taskapp.service;

import com.example.taskapp.model.Status;
import com.example.taskapp.model.Task;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author platoiscoding.com
 */
@Service
public interface TaskService {
    /**
     * GET all tasks from DB
     * @return all tasks from Database
     */
    Set<Task> getTasks();

    /**
     * finds a task by its ID
     * @param taskId    Database ID of task
     * @return          task
     */
    Task findById(Long taskId);

    /**
     * creates new Task and saves it in Database
     * @param taskDetails   field values
     * @return              new Task
     */
    Task createTask(Task taskDetails);

    /**
     * updates Task from Database with field values in taskDetails
     * @param taskId        Database ID of task
     * @param taskDetails   field values
     * @return              updated Task
     */
    Task updateTask(Long taskId, Task taskDetails);

    /**
     * deletes Task from Database
     * @param taskId    Database ID of task
     */
    void deleteTask(Long taskId);

    /**
     * Sets Task.Status to CLOSED
     * @param taskId    Database ID of task
     */
    void closeTask(Long taskId);

    /**
     * Sets Task.Status to REOPENED
     * @param taskId    Database ID of task
     */
    void reopenTask(Long taskId);

    /**
     * Filters all Tasks by Status
     * @param status    Enum: OPEN;CLOSED;REOPENED
     * @return          Set of Tasks
     */
    Set<Task> getTasksByStatus(Status status);

}
