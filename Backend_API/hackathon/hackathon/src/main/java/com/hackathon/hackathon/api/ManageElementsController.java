package com.hackathon.hackathon.api;


import com.hackathon.hackathon.model.Room;
import com.hackathon.hackathon.model.Sensor;
import com.hackathon.hackathon.services.RoomService;
import com.hackathon.hackathon.services.SensorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/management")
@RequiredArgsConstructor
@Tag(name="Management")
public class ManageElementsController {

    @Autowired
    private SensorService sensorService;

    @Autowired
    private RoomService roomService;

    @PostMapping("/sensor/{sensor_id}/{accept_status}")
    public ResponseEntity<Object> registerSensor(@PathVariable("sensor_id") Long sensorID, @PathVariable("accept_status") boolean acceptStatus) {
        Optional<Sensor> sensorOptional = sensorService.getSensorByID(sensorID);

        if (sensorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sensor not found");
        }

        Sensor sensor = sensorOptional.get();
        sensor.setAccepted(acceptStatus);

        Sensor updatedSensor = sensorService.updateSensor(sensor);

        return ResponseEntity.ok(updatedSensor);
    }

    @PostMapping("/room")
    public ResponseEntity<Object> createRoom(@RequestParam String name, @RequestParam int size, @RequestParam int maxPeople) {
        roomService.createRoom(name, size, maxPeople);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/sensor/{sensor_id}/room/{room_id}")
    public ResponseEntity<Object> assignRoomToSensor(@PathVariable("sensor_id") Long sensorId, @PathVariable("room_id") Long roomId) {
        Optional<Sensor> sensorOptional = sensorService.getSensorByID(sensorId);
        Optional<Room> roomOptional = roomService.getRoomByID(roomId);

        if (sensorOptional.isEmpty() || roomOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sensor or Room not found");
        }

        Sensor sensor = sensorOptional.get();
        Room room = roomOptional.get();
        sensorService.setRoom(sensor, room);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/sensor/{sensor_id}/remove-room")
    public ResponseEntity<Object> removeRoomFromSensor(@PathVariable("sensor_id") Long sensorId) {
        Optional<Sensor> sensorOptional = sensorService.getSensorByID(sensorId);

        if (sensorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sensor not found");
        }

        Sensor sensor = sensorOptional.get();
        sensorService.removeSensorFromRoom(sensor);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/sensor/{sensor_id}/update")
    public ResponseEntity<Object> updateSensor(@PathVariable("sensor_id") Long sensorId) {
        Optional<Sensor> sensorOptional = sensorService.getSensorByID(sensorId);

        if (sensorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sensor not found");
        }

        Sensor sensor = sensorOptional.get();
        sensorService.updateSensor(sensor);

        return ResponseEntity.status(HttpStatus.OK).build();
    }





}
