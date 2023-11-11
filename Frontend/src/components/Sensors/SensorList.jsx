import React, { useEffect, useState } from "react";
import SensorItem from "./SensorItem";
import "./SensorList.css";

const SensorList = ({ isOpen, onRequestClose }) => {
    const [roomData, setRoomData] = useState([]);
    const [sensorData, setSensorData] = useState([]);

    useEffect(() => {
        fetch("http://136.244.87.238:8080/api/v1/web-app/unAcceptedSensors")
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                setSensorData(data);
            })
            .catch((error) => {
                console.error(error);
            });
    }, []);

    const updateSensor = (roomId, selectedSensor) => {
        if (selectedSensor) {
            const requestBody = {
                accepted: true,
                updateRateInMS: 0,
                roomID: roomId,
            };

            fetch(
                `http://136.244.87.238:8080/api/v1/management/sensor/${selectedSensor}/update`,
                {
                    // Hier war ein überflüssiges Anführungszeichen, das entfernt wurde
                    method: "POST",
                    body: JSON.stringify(requestBody),
                    headers: {
                        "Content-Type": "application/json",
                    },
                }
            )
                .then((response) => {
                    if (response.ok) {
                        console.log("Sensor erfolgreich aktualisiert.");
                    } else {
                        console.error("Fehler beim Aktualisieren des Sensors.");
                    }
                })
                .catch((error) => {
                    console.error(error);
                });
        } else {
            console.error("Bitte wählen Sie zuerst einen Sensor aus.");
        }
    };

    return (
        <section>
            {sensorData.map((room) => (
                <SensorItem
                    key={room.sensorID}
                    id={room.sensorID}
                    name={room.type}
                    sensorData={room}
                    onUpdateSensor={updateSensor}
                />
            ))}
            ;
        </section>
    );
};

export default SensorList;
