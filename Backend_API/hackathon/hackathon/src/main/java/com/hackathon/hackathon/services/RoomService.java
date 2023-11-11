package com.hackathon.hackathon.services;

import com.hackathon.hackathon.model.Room;
import com.hackathon.hackathon.model.Sensor;
import com.hackathon.hackathon.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

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
}
