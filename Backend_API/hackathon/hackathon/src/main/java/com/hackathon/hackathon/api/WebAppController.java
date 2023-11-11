package com.hackathon.hackathon.api;

import com.hackathon.hackathon.dto.RoomDTO;
import com.hackathon.hackathon.dto.SensorDTO;
import com.hackathon.hackathon.model.Room;
import com.hackathon.hackathon.model.Sensor;
import com.hackathon.hackathon.services.RoomService;
import com.hackathon.hackathon.services.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/web-app")
@RequiredArgsConstructor
public class WebAppController {

    @Autowired
    private SensorService sensorService;
    @Autowired
    private RoomService roomService;

    @GetMapping("/sensor/{sensor_id}")
    public ResponseEntity<SensorDTO> getSensorById(@PathVariable("sensor_id") Long sensorId) {
        Optional<Sensor> sensor = sensorService.getSensorByID(sensorId);
        return sensor.map(s -> new SensorDTO(s.getSensorID(), s.getType(), s.isAvailable(), s.isAccepted(), s.getUpdateRateInMS(),
                        s.getRoom() != null ? s.getRoom().getRoomID() : null))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/room/{room_id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable("room_id") Long roomId) {
        Optional<Room> room = roomService.getRoomByID(roomId);
        return room.map(r -> {
                    List<Long> sensorIDs = r.getSensors().stream()
                            .map(Sensor::getSensorID)
                            .collect(Collectors.toList());
                    return new RoomDTO(r.getRoomID(), r.getName(), r.getSize(), r.getMaxPeople(), sensorIDs);
                })
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



    @GetMapping("/sensors")
    public ResponseEntity<List<SensorDTO>> getAllSensors() {
        List<Sensor> sensors = sensorService.getAllSensors();
        List<SensorDTO> sensorDTOs = sensors.stream()
                .map(s -> new SensorDTO(s.getSensorID(), s.getType(), s.isAvailable(), s.isAccepted(), s.getUpdateRateInMS(),
                        s.getRoom() != null ? s.getRoom().getRoomID() : null))
                .collect(Collectors.toList());
        return ResponseEntity.ok(sensorDTOs);
    }



    @GetMapping("/rooms")
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomDTO> roomDTOs = rooms.stream()
                .map(r -> new RoomDTO(r.getRoomID(), r.getName(), r.getSize(), r.getMaxPeople(),
                        r.getSensors().stream().map(Sensor::getSensorID).collect(Collectors.toList())))
                .collect(Collectors.toList());
        return ResponseEntity.ok(roomDTOs);
    }


}