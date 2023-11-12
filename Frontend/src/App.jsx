import { Route, Routes } from "react-router-dom";
import "./App.css";
import HomePage from "./pages/HomePage";
import AllRooms from "./pages/AllRooms";
import AddSensor from "./components/Sensors/SensorList";
import SidenavBar from "./components/Home/Sidenavbar";

function App() {
    return (
        <div>
            <SidenavBar />
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/all-rooms" element={<AllRooms />} />
                <Route path="/add-sensor" Component={AddSensor} />
            </Routes>
        </div>
    );
}

export default App;
