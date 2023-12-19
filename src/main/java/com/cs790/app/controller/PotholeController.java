package com.cs790.app.controller;

import com.cs790.app.model.Pothole;
import com.cs790.app.service.PotholeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PotholeController {

    @Autowired
    private PotholeService potholeService;

    @GetMapping(path = "/test")
    public ResponseEntity<String> testMethod(){
        return ResponseEntity.ok().body("This is a test endpoint");
    }

    @PostMapping(path = "/model-test")
    public ResponseEntity<Pothole> testPothole(@RequestBody Pothole pothole){

        return ResponseEntity.ok().body(potholeService.submitPotholeInfo(pothole));
    }
}
