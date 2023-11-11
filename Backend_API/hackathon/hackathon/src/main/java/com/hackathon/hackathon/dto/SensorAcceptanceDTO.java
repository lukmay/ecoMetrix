package com.hackathon.hackathon.dto;

import com.hackathon.hackathon.model.SensorType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SensorAcceptanceDTO {

    private boolean accepted;

    private int updateRateInMS;

    private Long roomID;
}
