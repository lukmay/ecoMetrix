package com.hackathon.hackathon.services;

import com.hackathon.hackathon.dto.CompanyOverviewDTO;
import com.hackathon.hackathon.dto.dataTime;
import com.hackathon.hackathon.model.Room;
import com.hackathon.hackathon.model.Sensor;
import com.hackathon.hackathon.model.SensorData;
import com.hackathon.hackathon.model.SensorType;
import com.hackathon.hackathon.repositories.RoomRepository;
import com.hackathon.hackathon.repositories.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SensorDataRepository sensorDataRepository;

    public Room createRoom(String name, int size, int maxPeople){
        Room room = new Room();
        room.setName(name);
        room.setSize(size);
        room.setMaxPeople(maxPeople);
        room.setSensors(Collections.emptyList());
        roomRepository.save(room);
        return room;
    }

    public Room setRoomName(Room room, String newName){
        room.setName(newName);
        roomRepository.save(room);
        return room;
    }


    public Optional<Room> getRoomByID(Long roomId) {
        return roomRepository.findById(roomId);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }


    public List<CompanyOverviewDTO> getSensorSummary(LocalDateTime from, LocalDateTime to) {
        List<SensorData> sensorDataList = sensorDataRepository.findByTimestampBetween(from, to);

        Map<SensorType, List<SensorData>> groupedByType = sensorDataList.stream()
                .collect(Collectors.groupingBy(sensorData -> sensorData.getSensor().getType()));

        List<CompanyOverviewDTO> summaries = new ArrayList<>();
        for (Map.Entry<SensorType, List<SensorData>> entry : groupedByType.entrySet()) {
            List<dataTime> dataTimes = entry.getValue().stream()
                    .map(sensorData -> new dataTime(sensorData.getTimestamp(), sensorData.getValue()))
                    .collect(Collectors.toList());

            summaries.add(new CompanyOverviewDTO(entry.getKey(), dataTimes));
        }

        return summaries;
    }


}
