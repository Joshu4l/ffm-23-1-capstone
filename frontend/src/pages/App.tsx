import './App.css';
import {Navigate, Route, Routes} from 'react-router-dom';
import Header from '../components/Header.tsx';
import Footer from '../components/Footer.tsx';
import Home from './Home.tsx';
import useGeolocation from "../custom-hooks/useGeolocation.tsx";
import UserlocationForm from './UserlocationForm.tsx';
import UserlocationDetails from "./UserlocationDetails.tsx";
import UserlocationGallery from "./UserlocationGallery.tsx";
import SliderPage from "./SliderPage.tsx";
  
  
export default function App() {

    // CUSTOM HOOK & STATE
    const { location, determineGeolocation } = useGeolocation();


    return (
        <div className="app-container">

            <Header />

            <div className="app-content">
                <Routes>

                    <Route
                        path="/"
                        element={<Home
                                    location={location}
                                    determineGeolocation={determineGeolocation}
                        />}
                    />

                    <Route
                        path="/userlocations/create"
                        element={<UserlocationForm
                                    latitude={location.coordinates.lat}
                                    longitude={location.coordinates?.lng}
                        />}
                    />

                    <Route
                        path="/userlocations/:id"
                        element={<UserlocationDetails/>}
                    />

                    <Route
                        path="/userlocations"
                        element={<UserlocationGallery/>}
                    />

                    <Route
                        path="/*"
                        element={<Navigate to="/"/>}
                    />

                    <Route
                        path="/recommendations"
                        element={<SliderPage/>}
                    />


                </Routes>
            </div>

            <Footer />
        </div>
    );
}
