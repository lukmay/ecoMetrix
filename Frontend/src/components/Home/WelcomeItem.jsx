import AddSensor from "../Sensors/SensorList";
import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./WelcomeItem.css";

const WelcomeItem = () => {
    return (
        <div className="welcome_item">
            <div>
                <h1>Welcome back, Hope!</h1>
                <p>Let's have a look at your company's consumption!</p>
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
