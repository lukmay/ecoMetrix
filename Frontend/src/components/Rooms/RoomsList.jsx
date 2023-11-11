import React, { useState, useEffect } from "react";
import RoomItem from "./RoomItem";

const RoomList = () => {
    const [data, setData] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchRoomList = async () => {
        setIsLoading(true);
        setError(null);
        try {
            const response = await fetch(
                "http://136.244.87.238:8080/api/v1/web-app/rooms"
            );
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const roomData = await response.json();
            setData(roomData);
        } catch (error) {
            setError("Error fetching data: " + error.message);
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        fetchRoomList();
    }, []);

    // Rendering
    if (isLoading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;

    console.log(data); // Hier füge den console.log hinzu
    return (
        <div>
            {data.map((room) => (
                // Für jeden Raum in den abgerufenen Daten erstellst du ein RoomItem und übergibst die Daten als Props
                <RoomItem key={room.id} room={room} />
            ))}
        </div>
    );
};

export default RoomList;
