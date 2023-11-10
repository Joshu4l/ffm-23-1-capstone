import "./Header.css";
import Menu from "./Menu";
import { useEffect, useState } from "react";
import sprocketSvg from "../assets/sprocket.svg";
import menuSvg from "../assets/burger-menu.svg";
import { Link } from "react-router-dom";

export default function Header() {
    // STATE
    const [isMenuOpen, setIsMenuOpen] = useState<boolean>(false);
    const [fontSize, setFontSize] = useState<number>(30);
    const [buttonSize, setButtonSize] = useState<number>(36); // Grundlegende Button-Größe


    // CALLBACKS
    const openMenu = () => {
        setIsMenuOpen(true);
    };

    const closeMenu = () => {
        setIsMenuOpen(false);
    };

    useEffect(() => {
        const handleResize = () => {

            const maxSize = 40;
            const windowWidth = window.innerWidth;
/*            const newFontSize = Math.min((windowWidth / 1200) * 40, maxSize);
            const newButtonSize = Math.min((windowWidth / 1200) * 60, 75); // Maximal 75px Breite*/

            const newFontSize = Math.min((windowWidth / 1200) * 70, maxSize); // Increase the factor (e.g., from 40 to 50)
            const newButtonSize = Math.min((windowWidth / 1200) * 95, 75); // Adjust as needed

            setFontSize(newFontSize);
            setButtonSize(newButtonSize);
        };

        // Event-Listener für Resize hinzufügen
        window.addEventListener("resize", handleResize);

        // Initialen Font-Size setzen
        handleResize();

        // Event-Listener beim Unmount entfernen
        return () => {
            window.removeEventListener("resize", handleResize);
        };
    }, []);

    return (
        <>
            <div className="navbar">
                <div className="burger-menu">
                    <button
                        className="burger-menu-button"
                        style={{ width: `${buttonSize}px` }}
                        onClick={openMenu}
                    >
                        <img id="burger-menu-svg" src={menuSvg} alt="burger-menu" />
                    </button>
                </div>
                <Link to={`/`}>
                    <h2 className="page-header" style={{ fontSize: `${fontSize}px` }}>
                        GroupsetHero
                    </h2>
                </Link>
                <img
                    id="sprocket-svg"
                    src={sprocketSvg}
                    alt="sprocket image"
                    style={{ height: `${buttonSize}px`, width: `${buttonSize}px` }}
                />
            </div>

            <Menu isOpen={isMenuOpen} closeMenu={closeMenu} />
        </>
    );
}
