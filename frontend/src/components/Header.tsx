import "./Header.css"
import Menu from "./Menu";
import {useState} from "react"; // Importiere die Menükomponente
import sprocketSvg from "../assets/sprocket.svg"
import menuSvg from "../assets/burger-menu.svg"

export default function Header() {

    // STATE
    const [isMenuOpen, setMenuOpen] = useState<boolean>(false);

    // CALLBACKS
    const openMenu = () => {
        setMenuOpen(true);
    };

    const closeMenu = () => {
        setMenuOpen(false);
    };


    return (
        <>

            <div className="navbar">
                <img
                    id="burger-menu-svg"
                    src={menuSvg}
                    alt=""
                    onClick={openMenu}
                />
                <h2>GroupsetHero</h2>{" "}
                <img id="sprocket-svg" src={sprocketSvg} alt="sprocket image" />
            </div>

            <Menu isOpen={isMenuOpen} closeMenu={closeMenu} />

        </>
    );

}