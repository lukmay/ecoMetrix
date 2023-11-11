package com.hackathon.hackathon.repositories;

import com.hackathon.hackathon.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
