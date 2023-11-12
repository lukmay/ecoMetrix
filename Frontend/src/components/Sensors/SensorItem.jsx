import React, { useState } from "react";
import "./SensorItem.css";

const SensorItem = ({ id, name, sensorData, onUpdateSensor }) => {
    const [selectedSensor, setSelectedSensor] = useState("");
    console.log(sensorData);

    const handleUpdateClick = () => {
        onUpdateSensor(id, selectedSensor);
    };

    return (
        <div className="sensor-item">
            {/* <h2>ID: {id}</h2> */}
            <p>
                Sensorname: <span>{name}</span>
            </p>
            {sensorData && (
                <div>
                    <label htmlFor={`sensorDropdown-${id}`}>
                        Wähle einen Sensor:
                    </label>
                    <div className="select-wrapper">
                        <select
                            id={`sensorDropdown-${id}`}
                            value={selectedSensor}
                            onChange={(e) => setSelectedSensor(e.target.value)}
                        >
                            <option value="">-- Wähle einen Sensor --</option>
                            <option
                                key={sensorData.sensorID}
                                value={sensorData.sensorID}
                            >
                                {sensorData.type}
                            </option>
                        </select>
                    </div>
                    <button className="add_btn" onClick={handleUpdateClick}>
                        Update Sensor
                    </button>
                </div>
            )}
        </div>
    );
};

export default SensorItem;
