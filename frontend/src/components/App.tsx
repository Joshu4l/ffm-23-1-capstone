
import { Route, Routes } from "react-router-dom";
import UserlocationForm from "./UserlocationForm";


import './App.css'
import Header from "./Header.tsx";
import Footer from "./Footer.tsx";
import Home from "./Home.tsx"


export default function App() {


    return (
        <div>
            <Header />
            <div className="introductionBox">
                Welcome to GroupsetHero - your assistant for equipping your road bike with the optimal groupset!
                We want to help you ensure you're always pedaling with ease, considering the terrain and elevation levels of your environment. :)
                ay goodbye to bobbing from too light gears as well as to breaking a sweat from grinding on too heavy ones.

            </div>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/userlocation" element={<UserlocationForm />} />
                </Routes>
            <Footer />
        </div>
    );

}
