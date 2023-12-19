package com.cs790.app.controller;

import com.cs790.app.config.S3UploadService;
import com.cs790.app.model.Task;
import com.cs790.app.service.TaskService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    S3UploadService s3UploadService;

    private TaskService taskService;


    @Autowired
    public TaskController(S3UploadService s3UploadService, TaskService taskService) {
        this.s3UploadService = s3UploadService;
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(@RequestParam("userId") String userId) {
        return ResponseEntity.ok().body(taskService.getAllTasks(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable String id) {
        return ResponseEntity.ok().body(taskService.findTaskById(id));
    }
    /*@PostMapping
    public Task createTask(@RequestParam Task task,
                           @RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
        return taskService.saveTask(task,file);
    }*/

    @PostMapping
    public ResponseEntity<String> createTask(@RequestParam(value = "taskJson") String taskJson,
                           @RequestPart(value = "file",required = false) MultipartFile file) throws IOException {
        Gson gson = new Gson();
        Task task = gson.fromJson(taskJson, Task.class);
        return ResponseEntity.ok().body(taskService.upsertTask(task,file));
    }



    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @RequestBody Task updatedTask) {
        return ResponseEntity.ok().body(taskService.updateTask(id, updatedTask));
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        taskService.removeTask(id);

    }

    @PostMapping("/upload-file")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile){
        try{
            return ResponseEntity.ok().body(s3UploadService.uploadFile(multipartFile));
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok().body("Upload failed");
    }

    @DeleteMapping("/remove-file")
    public ResponseEntity<?> removeFile(@RequestParam("file_name") String fileName){
        try{
            return ResponseEntity.ok().body(s3UploadService.removeFile(fileName));
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok().body("Deletion failed");
    }
}
