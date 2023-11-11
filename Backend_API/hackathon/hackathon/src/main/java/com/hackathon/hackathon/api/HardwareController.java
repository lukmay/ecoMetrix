package com.hackathon.hackathon.api;

import com.hackathon.hackathon.dto.SensorDataRequest;
import com.hackathon.hackathon.model.Sensor;
import com.hackathon.hackathon.model.SensorData;
import com.hackathon.hackathon.model.SensorType;
import com.hackathon.hackathon.services.SensorDataService;
import com.hackathon.hackathon.services.SensorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/sensor")
@RequiredArgsConstructor
@Tag(name="Hardware")
public class HardwareController {

    @Autowired
    SensorService sensorService;
    @Autowired
    SensorDataService sensorDataService;

    @PostMapping("/create/{sensor_type}")
    public ResponseEntity<Object> registerSensor(@PathVariable("sensor_type") SensorType sensorType) {
        Sensor sensor = sensorService.createSensor(sensorType);
        Long sensorId = sensor.getSensorID();

        return ResponseEntity.status(HttpStatus.CREATED).body(sensorId);
    }

    @PostMapping("/data/{sensor_id}")
    public ResponseEntity<Object> addSensorData(@PathVariable("sensor_id") Long sensorId, @RequestBody SensorDataRequest sensorDataRequest) {
        Optional<Sensor> sensorOptional = sensorService.getSensorByID(sensorId);

        if (sensorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sensor not found");
        }

        SensorData sensorData = new SensorData();
        sensorData.setValue(sensorDataRequest.getValue());
        sensorData.setTimestamp(LocalDateTime.now());
        sensorData.setSensor(sensorOptional.get());

        sensorDataService.updateData(sensorData);

        return ResponseEntity.status(HttpStatus.CREATED).body(sensorData);
    }

}
