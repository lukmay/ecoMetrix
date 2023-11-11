package com.hackathon.hackathon.dto;

import com.hackathon.hackathon.model.SensorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataMeanType {

    SensorType sensorType;
    Float meanData;

}
