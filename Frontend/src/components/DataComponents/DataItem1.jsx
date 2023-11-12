import React from "react";
import {
    AreaChart,
    Area,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    ResponsiveContainer,
    Legend,
} from "recharts";

const DataItem1 = ({ data }) => {
    console.log({ data });
    if (!data) {
        return null;
    }

    const temperatures = data.dataTimeList.map((item) => item.data);
    const minTemperature = Math.min(...temperatures);
    const maxTemperature = Math.max(...temperatures);

    return (
        <div className="item_one">
            <h1>{data.sensorType}</h1>
            <ResponsiveContainer width="100%" height={300}>
                <AreaChart data={data.dataTimeList}>
                    <XAxis dataKey="localDateTime" />
                    <YAxis
                        domain={[minTemperature, maxTemperature]}
                        tickFormatter={(value) => `${value} °C`}
                    />
                    <CartesianGrid stroke="#f5f5f5" />
                    <Tooltip />
                    <Legend />
                    <Area
                        type="monotone"
                        dataKey="data"
                        stroke="#8884d8"
                        fill="#8884d8"
                    />
                </AreaChart>
            </ResponsiveContainer>
        </div>
    );
};

export default DataItem1;
