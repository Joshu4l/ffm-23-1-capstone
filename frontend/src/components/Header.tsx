import "./Header.css";
import Menu from "./Menu";
import { useState } from "react";
import sprocketSvg from "../assets/sprocket.svg";
import menuSvg from "../assets/burger-menu.svg";
import {Link} from "react-router-dom";

export default function Header() {
    // STATE
    const [isMenuOpen, setIsMenuOpen] = useState<boolean>(false);

    // CALLBACKS
    const openMenu = () => {
        setIsMenuOpen(true);
    };

    const closeMenu = () => {
        setIsMenuOpen(false);
    };

    return (
        <>
            <div className="navbar">
                <div className="burger-menu">
                    <button className="burger-menu-button" onClick={openMenu}>
                        <img
                            id="burger-menu-svg"
                            src={menuSvg}
                            alt="burger-menu"
                        />
                    </button>
                </div>
                <Link to={`/`}>
                    <h2 className="page-header" > GroupsetHero</h2>
                </Link>
                <img id="sprocket-svg" src={sprocketSvg} alt="sprocket image" />
            </div>

            <Menu isOpen={isMenuOpen} closeMenu={closeMenu} />
        </>
    );
}