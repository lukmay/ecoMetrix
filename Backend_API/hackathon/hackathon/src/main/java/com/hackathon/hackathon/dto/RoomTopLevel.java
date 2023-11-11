package com.hackathon.hackathon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomTopLevel {

    private List<dataTime> electricityList;
    private List<DataMeanType> dataMeanTypes;

}
