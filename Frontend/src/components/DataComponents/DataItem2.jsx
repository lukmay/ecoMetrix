import React from "react";
import "./DataItem1.css";
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

const DataItem2 = ({ data }) => {
    if (!data) {
        return null;
    }
    // const formattedData = data.dataTimeList.map((item) => ({
    //     ...item,
    //     localDateTime: formatLocalDateTime(item.localDateTime),
    // }));
    return (
        <div className="item_two">
            <h1>{data.sensorType}</h1>
            <ResponsiveContainer width="100%" height={300}>
                <AreaChart data={data.dataTimeList}>
                    <XAxis dataKey="localDateTime" />
                    <YAxis dataKey="data" />
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

export default DataItem2;
