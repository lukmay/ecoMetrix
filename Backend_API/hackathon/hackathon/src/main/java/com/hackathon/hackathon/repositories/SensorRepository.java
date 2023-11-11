package com.hackathon.hackathon.repositories;

import com.hackathon.hackathon.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    List<Sensor> findAllByAccepted(boolean accepted);

}
