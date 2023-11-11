package com.hackathon.hackathon.api;

import com.hackathon.hackathon.model.Sensor;
import com.hackathon.hackathon.model.SensorType;
import com.hackathon.hackathon.services.SensorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hardware")
@RequiredArgsConstructor
@Tag(name="Hardware")
public class HardwareController {

    @Autowired
    SensorService sensorService;

    @PostMapping("/{sensor_type}")
    public ResponseEntity<Object> registerSensor(@PathVariable("sensor_type") SensorType sensorType) {
        Sensor sensor = sensorService.createSensor(sensorType);
        Long sensorId = sensor.getSensorID();

        return ResponseEntity.status(HttpStatus.CREATED).body(sensorId);
    }

}
