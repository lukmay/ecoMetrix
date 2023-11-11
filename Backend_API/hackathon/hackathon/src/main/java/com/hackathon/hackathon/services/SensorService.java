package com.hackathon.hackathon.services;

import com.hackathon.hackathon.model.Room;
import com.hackathon.hackathon.model.Sensor;
import com.hackathon.hackathon.model.SensorData;
import com.hackathon.hackathon.model.SensorType;
import com.hackathon.hackathon.repositories.SensorDataRepository;
import com.hackathon.hackathon.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private SensorDataRepository sensorDataRepository;

    public Sensor createSensor(SensorType sensorType){
        Sensor sensor = new Sensor();
        sensor.setType(sensorType);
        sensor.setData(Collections.emptyList());
        sensor.setAccepted(false);
        sensor.setAvailable(false);
        sensorRepository.save(sensor);
        return sensor;

    }

    public void setAccepted(Sensor sensor, boolean status){
        sensor.setAccepted(status);
        sensorRepository.save(sensor);
    }

    public void setAvailable(Sensor sensor, boolean status){
        sensor.setAvailable(status);
        sensorRepository.save(sensor);
    }

    public SensorData addSensorData(Sensor sensor, float value) {
        SensorData sensorData = new SensorData();
        sensorData.setTimestamp(LocalDateTime.now());
        sensorData.setValue(value);
        sensorData.setSensor(sensor);

        sensorDataRepository.save(sensorData);
        return sensorData;
    }

    public List<SensorData> getSensorData(Sensor sensor, LocalDateTime start, LocalDateTime end){
        return sensorDataRepository.findBySensorAndTimestampBetween(sensor, start, end);
    }

    public void setRoom(Sensor sensor, Room room){
        sensor.setRoom(room);
        sensorRepository.save(sensor);
    }

    public Optional<Sensor> getSensorByID(Long sensorID){
        return sensorRepository.findById(sensorID);
    }

    public Sensor updateSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }


    public void removeSensorFromRoom(Sensor sensor) {
        sensor.setRoom(null);
    }

    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }
}
