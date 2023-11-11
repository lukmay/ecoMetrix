package com.hackathon.hackathon.services;

import com.hackathon.hackathon.model.SensorData;
import com.hackathon.hackathon.repositories.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorDataService {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    public void updateData(SensorData data) {
        sensorDataRepository.save(data);
    }

}
