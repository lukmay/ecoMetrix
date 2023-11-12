import React, { useState, useEffect } from "react";
import "./HomePage.css";
import WelcomeItem from "../components/Home/WelcomeItem";
import Headline from "../components/Home/Headline";
import DashboardOverview from "../components/Home/DashboardOverview";

const HomePage = () => {
    return (
        <main>
            <section className="grid-container">
                <WelcomeItem />
                <Headline />
                <DashboardOverview />
            </section>
            <p className="love">Made with ♥️</p>
        </main>
    );
};

export default HomePage;
