import AddSensor from "./Sensors/SensorList";
import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./WelcomeItem.css";

const HeadlineStatic = () => {
    return (
        <div className="headline_item">
            <h1>YOUR COMPANY DASHBOARD</h1>
        </div>
    );
};

export default HeadlineStatic;
