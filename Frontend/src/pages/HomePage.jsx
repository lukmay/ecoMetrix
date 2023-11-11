import React, { useState, useEffect } from "react";
import SidenavBar from "../components/Sidenavbar";
import DataItem1 from "../components/DataComponents/DataItem1";
import DataItem2 from "../components/DataComponents/DataItem2";
import DataItem3 from "../components/DataComponents/DataItem3";
import DataItem4 from "../components/DataComponents/DataItem4";
import "./HomePage.css";
import WelcomeItem from "../components/WelcomeItem";
import Headline from "../components/Headline";

const HomePage = () => {
    const [summary, setSummary] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    // Function to fetch sensor data
    const fetchSensorSummary = async (fromDate, toDate) => {
        setIsLoading(true);
        setError(null);
        try {
            const fromDateTime = fromDate.toISOString();
            const toDateTime = toDate.toISOString();
            const response = await fetch(
                `http://136.244.87.238:8080/api/v1/web-app/summaryAll?from=${fromDateTime}&to=${toDateTime}`
            );
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            setSummary(data);
        } catch (error) {
            setError("Error fetching data: " + error.message);
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        // Fetch data initially
        // const toDate = new Date(); // Aktuelles Datum und Uhrzeit
        // const fromDate = new Date(toDate - 5 * 60 * 1000); // 5 Minuten zurück
        const fromDate = new Date(); // Current date and time
        fromDate.setMinutes(fromDate.getMinutes() - 3);
        const toDate = new Date();
        fetchSensorSummary(fromDate, toDate);

        // Set up interval for real-time data fetching
        const interval = setInterval(() => {
            fetchSensorSummary(fromDate, toDate);
        }, 15000);
        return () => clearInterval(interval);
    }, []);

    // Rendering
    if (isLoading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;

    console.log("Summary Data:", summary); // Hier füge den console.log hinzu

    return (
        <main>
            <section className="grid-container">
                <WelcomeItem />
                <Headline />
                {summary.map((sensorData, index) => {
                    switch (index) {
                        case 0:
                            return <DataItem1 key={index} data={sensorData} />;
                        case 1:
                            return <DataItem2 key={index} data={sensorData} />;
                        case 2:
                            return <DataItem3 key={index} data={sensorData} />;
                        case 3:
                            return <DataItem4 key={index} data={sensorData} />;
                        default:
                            return null;
                    }
                })}
            </section>
        </main>
    );
};

export default HomePage;
