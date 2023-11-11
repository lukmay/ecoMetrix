import React, { useState } from "react";

const SensorItem = ({ id, name, sensorData, onUpdateSensor }) => {
    const [selectedSensor, setSelectedSensor] = useState("");
    console.log(sensorData);

    const handleUpdateClick = () => {
        onUpdateSensor(id, selectedSensor);
    };

    return (
        <div className="additional-sensor">
            {/* <h2>ID: {id}</h2> */}
            <p>
                Sensorname: <span>{name}</span>
            </p>
            {sensorData && (
                <div>
                    <label htmlFor={`sensorDropdown-${id}`}>
                        Wähle einen Sensor:
                    </label>
                    <select
                        id={`sensorDropdown-${id}`}
                        value={selectedSensor}
                        onChange={(e) => setSelectedSensor(e.target.value)}
                    >
                        <option value="">-- Wähle einen Sensor --</option>
                        <option
                            key={sensorData.sensorID} // Beachten Sie, dass dies nicht mehr notwendig ist
                            value={sensorData.sensorID}
                        >
                            {sensorData.type}
                        </option>
                    </select>
                    <button className="add_btn" onClick={handleUpdateClick}>
                        Update Sensor
                    </button>
                </div>
            )}
        </div>
    );
};

export default SensorItem;
