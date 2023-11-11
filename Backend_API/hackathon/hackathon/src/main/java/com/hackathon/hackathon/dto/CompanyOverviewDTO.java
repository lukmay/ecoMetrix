package com.hackathon.hackathon.dto;

import com.hackathon.hackathon.model.SensorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyOverviewDTO {
    SensorType sensorType;
    List<dataTime> dataTimeList;

}
