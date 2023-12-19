package com.cs790.app.repository;

import com.cs790.app.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByIsCompleted(boolean b);
    List<Task> findByIsCompletedAndUserId(boolean b, String userId);

}
