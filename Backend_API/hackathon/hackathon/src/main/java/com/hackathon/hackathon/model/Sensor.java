package com.hackathon.hackathon.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sensor")
public class Sensor {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sensorID;

    private SensorType type;

    private boolean available;

    private boolean accepted;

    private int updateRateInMS;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany
    private List<SensorData> data;

}
