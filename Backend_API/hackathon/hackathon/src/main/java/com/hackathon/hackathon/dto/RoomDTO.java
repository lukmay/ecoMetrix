package com.hackathon.hackathon.dto;

import com.hackathon.hackathon.model.Sensor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class RoomDTO {

    private Long roomID;
    private String name;
    private int size;
    private int maxPeople;
    private List<Long> sensorIDs;
    
}
