package com.cs790.app.service;

import com.cs790.app.model.Task;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TaskService {

    String upsertTask(Task task, MultipartFile file) throws IOException;
    List<Task> getAllTasks(String userId);

    Task findTaskById(String id);

    Task updateTask(String id, Task updatedTask);

    void removeTask(String id);
}
