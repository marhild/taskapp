package com.example.taskapp.service;

import com.example.taskapp.model.Status;
import com.example.taskapp.model.Task;
import com.example.taskapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author platoiscoding.com
 */
@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    private TaskRepository taskRepository;

    /**
     * GET all tasks from DB
     * @return all tasks from Database
     */
    @Override
    public Set<Task> getTasks() {
        Set<Task> taskSet = new HashSet<>();
        taskRepository.findAll().iterator().forEachRemaining(taskSet::add);
        return taskSet;
    }

    /**
     * finds a task by its ID
     * @param taskId    Database ID of task
     * @return          task
     */
    @Override
    public Task findById(Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if(!taskOptional.isPresent()){
            throw new RuntimeException("Task Not Found!");
        }
        return taskOptional.get();
    }

    /**
     * creates new Task and saves it in Database
     * @param taskDetails   field values
     * @return              new Task
     */
    @Override
    public Task createTask(Task taskDetails) {
        Task newTask = taskRepository.save(taskDetails);
        return newTask;
    }

    /**
     * updates Task from Database with field values in taskDetails
     * @param taskId        Database ID of task
     * @param taskDetails   field values
     */
    @Override
    public Task updateTask(Long taskId, Task taskDetails) {
        Task task = findById(taskId);
        task.setTitle(taskDetails.getTitle());
        task.setContent(taskDetails.getContent());
        task.setStatus(taskDetails.getStatus());
        taskRepository.save(task);
        return task;
    }

    /**
     * deletes Task from Database
     * @param taskId    Database ID of task
     */
    @Override
    public void deleteTask(Long taskId) {
        taskRepository.delete(findById(taskId));
    }

    /**
     * Sets Task.Status to CLOSED
     * @param taskId    Database ID of task
     */
    @Override
    public void closeTask(Long taskId) {
        Task task = findById(taskId);
        task.closeTask();
        taskRepository.save(task);
    }

    /**
     * Sets Task.Status to REOPENED
     * @param taskId    Database ID of task
     */
    @Override
    public void reopenTask(Long taskId) {
        Task task = findById(taskId);
        task.reopenTask();
        taskRepository.save(task);
    }

    /**
     * Filters all Tasks by Status
     * @param status    Enum: OPEN;CLOSED;REOPENED
     * @return          Set of Tasks
     */
    @Override
    public Set<Task> getTasksByStatus(Status status) {
        Set<Task> allTasks = getTasks();
        Set<Task> filteredTasks = new HashSet<>();

        for(Task t : allTasks){
            if(t.getStatus().equals(status)){filteredTasks.add(t);}
        }

        return filteredTasks;
    }
}
