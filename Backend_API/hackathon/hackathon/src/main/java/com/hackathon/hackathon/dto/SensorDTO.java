package com.hackathon.hackathon.dto;

import com.hackathon.hackathon.model.Room;
import com.hackathon.hackathon.model.SensorData;
import com.hackathon.hackathon.model.SensorType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SensorDTO {
    private Long sensorID;

    private SensorType type;

    private boolean available;

    private boolean accepted;

    private int updateRateInMS;

    private Long roomID;



}
