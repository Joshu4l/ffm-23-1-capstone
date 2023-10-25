import './App.css';
import { Route, Routes } from 'react-router-dom';
import Home from './Home.tsx';
import UserlocationForm from './UserlocationForm.tsx';
import Header from '../components/Header.tsx';
import Footer from '../components/Footer.tsx';
import IntroductionBox from '../components/IntroductionBox.tsx';
import useGeolocation from "../custom-hooks/useGeolocation.tsx";

export default function App() {

    // STATE
    const { location, determineGeolocation } = useGeolocation();


    return (
        <div style={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>

            <Header />

            <IntroductionBox />

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
                        path="/userlocation"
                        element={<UserlocationForm
                                 latitude={location.coordinates.lat}
                                 longitude={location.coordinates?.lng}
                                 />
                        }
                    />

                </Routes>
            </div>

            <Footer />

        </div>
        )
}
