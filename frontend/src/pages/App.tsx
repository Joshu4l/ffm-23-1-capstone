import './App.css';
import { Route, Routes } from 'react-router-dom';
import Home from './Home.tsx';
import UserlocationForm from './UserlocationForm.tsx';
import Header from '../components/Header.tsx';
import Footer from '../components/Footer.tsx';
import IntroductionBox from '../components/IntroductionBox.tsx';

export default function App() {
    return (
        <div style={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
            <Header />

            <IntroductionBox />

            <div style={{ flex: 1 }}>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/userlocation" element={<UserlocationForm />} />
                </Routes>
            </div>

            <Footer />
        </div>
    );
}
