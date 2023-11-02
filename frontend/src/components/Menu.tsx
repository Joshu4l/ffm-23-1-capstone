import "./Menu.css";
import {Link} from "react-router-dom";

interface MenuProps {
    isOpen: boolean;
    closeMenu: () => void;
}

export default function Menu({ isOpen, closeMenu }: MenuProps) {
    return (
        <div className={`menu ${isOpen ? "open" : ""}`}>

            <p className="navigation-option">
                    <Link to={"/"}>Home</Link>
            </p>

            <p className="navigation-option">
                    <Link to={"/"}>Option 2</Link>
            </p>

            <p className="navigation-option">
                    <Link to={"/"}>Option 3</Link>
            </p>


            <button id="collapse-menu-button" onClick={closeMenu}>
                &#xd7;
            </button>
        </div>
    );
}
