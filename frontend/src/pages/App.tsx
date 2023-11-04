import './App.css';
import {Navigate, Route, Routes, useNavigate} from 'react-router-dom';
import Home from './Home.tsx';
import UserlocationForm from './UserlocationForm.tsx';
import Header from '../components/Header.tsx';
import Footer from '../components/Footer.tsx';
import useGeolocation from "../custom-hooks/useGeolocation.tsx";
import UserlocationDetails from "./UserlocationDetails.tsx";
import axios from "axios";

export default function App() {

    // CUSTOM HOOK & STATE
    const { location, determineGeolocation } = useGeolocation();
    const navigate = useNavigate();


    // AXIOS
    function deleteUserlocationById(id: string) {
        axios.delete(`/api/userlocations/${id}`)
            .then(() => {
                navigate("/userlocations")
            })
/*            .then(() => {
                fetchMovieData()
            })*/
            .catch(
                (reason) => {console.log(reason)}
            )
    }



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
                        element={<UserlocationDetails
                                    deleteFunction={deleteUserlocationById}
                        />}
                    />

                    <Route
                        path="/*"
                        element={<Navigate to="/"
                        />}
                    />
                </Routes>
            </div>
            <Footer />
        </div>
    );
}
