import './App.css';
import {Navigate, Route, Routes} from 'react-router-dom';
import Home from './Home.tsx';
import UserlocationForm from './UserlocationForm.tsx';
import Header from '../components/Header.tsx';
import Footer from '../components/Footer.tsx';
import useGeolocation from "../custom-hooks/useGeolocation.tsx";

import UserlocationDetails from "./UserlocationDetails.tsx";


export default function App() {

    // CUSTOM HOOK & STATE
    const { location, determineGeolocation } = useGeolocation();

    // AXIOS



    return (
        <div style={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>

            <Header />

            <div style={{ flex: 1 }}>
                <Routes>

                    <Route
                        path="/"
                        element={<Home
                                 location={location}
                                 determineGeolocation={determineGeolocation}
                                 />
                        }
                    />

                    <Route
                        path="/userlocations/create"
                        element={<UserlocationForm
                                     latitude={location.coordinates.lat}
                                     longitude={location.coordinates?.lng}
                                 />
                        }
                    />

                    <Route path={"/userlocations/:id"}
                           element={<UserlocationDetails/>}
                    />

                    <Route
                        path="/*"
                        element={<Navigate
                                    to={"/"}
                                 />
                        }
                    />

                </Routes>
            </div>

            <Footer />

        </div>
        )
}
