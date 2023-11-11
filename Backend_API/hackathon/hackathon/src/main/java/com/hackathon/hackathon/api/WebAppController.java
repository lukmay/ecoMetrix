package com.hackathon.hackathon.api;

import com.hackathon.hackathon.dto.CompanyOverviewDTO;
import com.hackathon.hackathon.dto.RoomDTO;
import com.hackathon.hackathon.dto.SensorDTO;
import com.hackathon.hackathon.dto.SensorShortDTO;
import com.hackathon.hackathon.model.Room;
import com.hackathon.hackathon.model.Sensor;
import com.hackathon.hackathon.services.RoomService;
import com.hackathon.hackathon.services.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @GetMapping("/unAcceptedSensors")
    public ResponseEntity<List<SensorShortDTO>> getAllUnAcceptedSensors() {
        List<Sensor> unacceptedSensors = sensorService.getAllUnacceptedSensors();
        List<SensorShortDTO> sensorDTOs = new ArrayList<>();
        for (Sensor sensor : unacceptedSensors) {
            SensorShortDTO sensorShortDTO = new SensorShortDTO(sensor.getSensorID(), sensor.getType());
            sensorDTOs.add(sensorShortDTO);
        }

        return ResponseEntity.ok(sensorDTOs);
    }

    @GetMapping("/summaryAll")
    public ResponseEntity<List<CompanyOverviewDTO>> getSensorSummary(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        List<CompanyOverviewDTO> summary = roomService.getSensorSummary(from, to);
        return ResponseEntity.ok(summary);
    }

/*    @GetMapping("/summaryRooms")
    public ResponseEntity<List<CompanyOverviewDTO>> getSensorSummary() {

        List<CompanyOverviewDTO> summary = roomService.getSensorSummary(from, to);
        return ResponseEntity.ok(summary);
    }*/



}