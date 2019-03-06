package com.example.taskapp.repository;

import com.example.taskapp.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author platoiscoding.com
 */
@Repository
public interface TaskRepository extends CrudRepository<Task, Long>{
}
