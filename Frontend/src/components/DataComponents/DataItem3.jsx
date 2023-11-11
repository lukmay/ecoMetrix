import React from "react";
import {
    BarChart,
    Bar,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    Legend,
    ResponsiveContainer,
} from "recharts";

const DataItem3 = ({ data }) => {
    if (!data) {
        return null;
    }

    return (
        <div className="item_three">
            <h1>{data.sensorType}</h1>
            <ResponsiveContainer width="100%" height={300}>
                <BarChart data={data.dataTimeList}>
                    <XAxis dataKey="localDateTime" />
                    <YAxis dataKey="data" />
                    <CartesianGrid stroke="#f5f5f5" />
                    <Tooltip />
                    <Legend />
                    <Bar dataKey="data" fill="#8884d8" />
                </BarChart>
            </ResponsiveContainer>
        </div>
    );
};

export default DataItem3;
