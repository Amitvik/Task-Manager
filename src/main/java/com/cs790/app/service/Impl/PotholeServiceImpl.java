package com.cs790.app.service.Impl;

import com.cs790.app.model.Pothole;
import com.cs790.app.repository.PotholeRepository;
import com.cs790.app.service.PotholeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PotholeServiceImpl implements PotholeService {

    @Autowired
    private PotholeRepository potholeRepository;
    @Override
    public Pothole submitPotholeInfo(Pothole pothole) {
        Pothole pothole1 = pothole;
        pothole1.setId(UUID.randomUUID().toString());

        return potholeRepository.save(pothole1);
    }
}
