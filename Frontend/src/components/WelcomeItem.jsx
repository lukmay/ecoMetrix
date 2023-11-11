import AddSensor from "./Sensors/SensorList";
import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./WelcomeItem.css";

const WelcomeItem = () => {
    return (
        <div className="welcome_item">
            <div>
                <h1>Welcome back, Hope!</h1>
                <p>Look at how your company is doing so far!</p>
            </div>
            <div className="button-container">
                <Link to="/add-sensor" className="add_btn">
                    Add Sensor
                </Link>
            </div>
        </div>
    );
};

export default WelcomeItem;
