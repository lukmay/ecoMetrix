import React, { useState } from "react";
import {
    ScatterChart,
    Scatter,
    XAxis,
    YAxis,
    ZAxis,
    Tooltip,
    Legend,
    ResponsiveContainer,
    CartesianGrid,
} from "recharts";

const data = {
    SensorData: [
        {
            Room: "Conference Room",
            Type: "Electricity",
            Data: [
                {
                    Timestamp: "2023-10-21-22-22-07-390444",
                    Value: 595.62,
                },
                {
                    Timestamp: "2023-10-22-10-30-15-390444",
                    Value: 512.34,
                },
                {
                    Timestamp: "2023-10-23-18-45-09-390444",
                    Value: 623.18,
                },
                {
                    Timestamp: "2023-10-24-13-12-30-390444",
                    Value: 530.22,
                },
                {
                    Timestamp: "2023-10-25-21-03-47-390444",
                    Value: 610.75,
                },
            ],
        },
        {
            Room: "Conference Room",
            Type: "People",
            Data: [
                {
                    Timestamp: "2023-11-06-02-51-31-390598",
                    Value: 11,
                },
                {
                    Timestamp: "2023-11-07-09-22-45-390598",
                    Value: 14,
                },
                {
                    Timestamp: "2023-11-08-12-36-58-390598",
                    Value: 8,
                },
                {
                    Timestamp: "2023-11-09-15-14-07-390598",
                    Value: 12,
                },
                {
                    Timestamp: "2023-11-10-17-45-20-390598",
                    Value: 7,
                },
            ],
        },
        {
            Room: "Conference Room",
            Type: "AirQuality",
            Data: [
                {
                    Timestamp: "2023-10-20-20-57-12-390689",
                    Value: 410.57,
                },
                {
                    Timestamp: "2023-10-21-23-05-19-390689",
                    Value: 395.22,
                },
                {
                    Timestamp: "2023-10-22-11-32-33-390689",
                    Value: 420.88,
                },
                {
                    Timestamp: "2023-10-23-19-48-46-390689",
                    Value: 385.34,
                },
                {
                    Timestamp: "2023-10-24-14-15-59-390689",
                    Value: 405.47,
                },
            ],
        },
    ],
};

const parseTimestamp = (timestamp) => {
    const parts = timestamp.split("-").map((part) => parseInt(part, 10));
    const [year, month, day, hours, minutes, seconds, milliseconds] = parts;

    const parsedDate = new Date(
        Date.UTC(year, month - 1, day, hours, minutes, seconds, milliseconds)
    );

    return parsedDate;
};

const formatTimestamp = (timestamp) => {
    return (
        timestamp.toISOString().split("T")[0] +
        " " +
        timestamp.toISOString().split("T")[1].split(".")[0]
    );
};

const removeTimezone = (timestamp) => {
    const timestampString = timestamp.toISOString();
    return new Date(timestampString.slice(0, -1) + "Z");
};

const DataItem1 = () => {
    const peopleData = data.SensorData.find((item) => item.Type === "People");

    if (!peopleData) {
        return null;
    }

    // Konvertiere die Timestamps und entferne die Zeitzone-Informationen
    const convertedData = peopleData.Data.map((entry) => ({
        ...entry,
        Timestamp: formatTimestamp(
            removeTimezone(parseTimestamp(entry.Timestamp))
        ),
    }));

    return (
        <div className="item_one">
            <h1>People</h1>
            <ResponsiveContainer width="100%" height={300}>
                <ScatterChart
                    margin={{ top: 20, right: 30, bottom: 10, left: 10 }}
                >
                    <CartesianGrid stroke="#f5f5f5" />
                    <XAxis dataKey="Timestamp" type="category" />
                    <YAxis
                        dataKey="Value"
                        type="number"
                        domain={["auto", "auto"]}
                    />
                    <Tooltip
                        cursor={{ strokeDasharray: "3 3" }}
                        labelFormatter={() => null}
                    />
                    <Legend />
                    <Scatter
                        name="People"
                        data={convertedData}
                        fill="#8884d8"
                        shape="circle"
                    />
                </ScatterChart>
            </ResponsiveContainer>
        </div>
    );
};

export default DataItem1;
