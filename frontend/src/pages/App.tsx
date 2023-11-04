import './App.css';
import {Navigate, Route, Routes, useNavigate} from 'react-router-dom';
import {useState, useEffect} from "react";
import {Userlocation} from "../components/Entities.ts";
import Header from '../components/Header.tsx';
import Footer from '../components/Footer.tsx';
import Home from './Home.tsx';
import useGeolocation from "../custom-hooks/useGeolocation.tsx";
import UserlocationForm from './UserlocationForm.tsx';
import UserlocationDetails from "./UserlocationDetails.tsx";
import axios from "axios";
import UserlocationGallery from "./UserlocationGallery.tsx";
  
  
export default function App() {

    // CUSTOM HOOK & STATE
    const { location, determineGeolocation } = useGeolocation();
    const [userlocations, setUserlocations] = useState<Userlocation[]>([])
    const navigate = useNavigate()


    // AXIOS
    function fetchUserlocationData() {
        axios.get("/api/userlocations")
            .then(response => {
                setUserlocations(response.data)
            })
            .catch(reason => {
                console.error(reason)
            })
    }

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


    // RENDER BEHAVIOUR
    useEffect(() => {
        fetchUserlocationData();
    }, []);


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
                        path="/userlocations"
                        element={<UserlocationGallery
                            userlocations={userlocations}
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
