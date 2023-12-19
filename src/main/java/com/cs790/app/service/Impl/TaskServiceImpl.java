package com.cs790.app.service.Impl;

import com.cs790.app.config.ChatService;
import com.cs790.app.config.S3UploadService;
import com.cs790.app.model.Task;
import com.cs790.app.repository.TaskRepository;
import com.cs790.app.service.CloudWatchService;
import com.cs790.app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private final S3UploadService s3UploadService;
    private final TaskRepository taskRepository;

    private final ChatService chatService;

    @Autowired
    private CloudWatchService cwService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, S3UploadService S3UploadService, ChatService chatService) {
        this.taskRepository = taskRepository;
        this.s3UploadService = S3UploadService;
        this.chatService = chatService;
    }
    @Override
    public String upsertTask(Task task, MultipartFile file) throws IOException {
        String imageUrl = s3UploadService.uploadFile(file);

        // Set the image URL in the task entity
        task.setImageUrl(imageUrl);
        if(task.getId() == null){
            String id =  UUID.randomUUID().toString();
            task.setId(UUID.randomUUID().toString());
            cwService.logMessageToCloudWatch("New Task: "+ id +" creation in progress.");
        }
        // Save the task to the database
        Task savedTask = taskRepository.save(task);
        cwService.logMessageToCloudWatch("Task created");
        cwService.logMessageToCloudWatch("Asking chatgpt on how to complete this task: "+task.getId()+ " efficiently");
        String response = chatService.chat("How do I perfom this task efficiently: "+savedTask.getDescription()+ "by this date: "+savedTask.getDueDate());
        cwService.logMessageToCloudWatch("Received response");

        return response;
    }

    @Override
    public List<Task> getAllTasks(String userId) {
        return taskRepository.findByIsCompletedAndUserId(false, userId);
    }

    @Override
    public Task findTaskById(String id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public Task updateTask(String id, Task updatedTask) {
        Task existingTask = taskRepository.findById(id).orElse(null);

        if (existingTask != null) {
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setDueDate(updatedTask.getDueDate());
            existingTask.setCompleted(updatedTask.isCompleted());
            return taskRepository.save(existingTask);
        } else {
            // Handle not found case
            //Throw task not found exception
            return null;
        }
    }

    @Override
    public void removeTask(String id) {
        cwService.logMessageToCloudWatch("Attempting to delete task with id: "+id);
        cwService.logMessageToCloudWatch("Attempting to delete image file from s3 bucket");
        Optional<Task> task = taskRepository.findById(id);
        if(!task.isEmpty()){
            try{
                String[] parts = task.get().getImageUrl().replace("https://", "").split("/");
                String[] bucketRegion = parts[0].split("\\.");
                String bucketName = bucketRegion[0];
                String region = bucketRegion[2];
                String objectKey = String.join("/", Arrays.copyOfRange(parts, 1, parts.length));
                cwService.logMessageToCloudWatch("Extracted file name from url: "+ objectKey);
                s3UploadService.removeFile(objectKey);
                cwService.logMessageToCloudWatch("Successfully removed file from bucket: "+ bucketName);
                taskRepository.deleteById(id);
                cwService.logMessageToCloudWatch("Successfully deleted task");
            }catch(Exception e){
                cwService.logMessageToCloudWatch("Error removing file, hence task deletion failed");
            }
        }
    }
}
