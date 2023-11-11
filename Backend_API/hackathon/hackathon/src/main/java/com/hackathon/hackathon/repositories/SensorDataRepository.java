package com.hackathon.hackathon.repositories;

import com.hackathon.hackathon.model.Sensor;
import com.hackathon.hackathon.model.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {

    List<SensorData> findBySensorAndTimestampBetween(Sensor sensor, LocalDateTime timestamp, LocalDateTime timestamp2);


}
