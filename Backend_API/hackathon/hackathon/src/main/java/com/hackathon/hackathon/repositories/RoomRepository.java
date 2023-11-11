package com.hackathon.hackathon.repositories;

import com.hackathon.hackathon.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
