import React, { useState, useEffect } from "react";
import "./Sidenavbar.css";
import { Link, useLocation } from "react-router-dom";

const SidenavBar = () => {
    const [sidebarOpen, setSidebarOpen] = useState(false);
    const [activeItem, setActiveItem] = useState(null);
    const location = useLocation();

    useEffect(() => {
        // Setze das aktive Element basierend auf dem aktuellen Pfad
        if (location.pathname === "/") {
            setActiveItem("dashboard");
        } else if (location.pathname === "/all-rooms") {
            setActiveItem("rooms");
        }
    }, [location.pathname]);

    return (
        <aside className={`sidebar ${sidebarOpen ? "open" : ""}`}>
            <div className="sidebar_items">
                <h1 className="ecometrix_h1">
                    <Link to="/">EcoMetrix</Link>
                </h1>

                <div
                    className={`sidebar_item ${
                        activeItem === "dashboard" ? "active" : ""
                    } ${activeItem === "dashboard" ? "clicked" : ""}`}
                    onClick={() => setActiveItem("dashboard")}
                >
                    <p>
                        <Link to="/">Dashboard</Link>
                    </p>
                </div>
                <div
                    className={`sidebar_item ${
                        activeItem === "rooms" ? "active" : ""
                    } ${activeItem === "rooms" ? "clicked" : ""}`}
                    onClick={() => setActiveItem("rooms")}
                >
                    <p>
                        <Link to="/all-rooms">Rooms</Link>
                    </p>
                </div>
                <div className="sidebar_item">
                    <p>Suggestions</p>
                </div>
                <div className="sidebar_item">
                    <p>Settings</p>
                </div>
                <div className="sidebar_item">
                    <p>Download</p>
                </div>
            </div>
        </aside>
    );
};

export default SidenavBar;
