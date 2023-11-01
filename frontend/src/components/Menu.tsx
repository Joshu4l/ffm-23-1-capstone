import "./Menu.css";
import {Link} from "react-router-dom";

interface MenuProps {
    isOpen: boolean;
    closeMenu: () => void;
}

export default function Menu({ isOpen, closeMenu }: MenuProps) {

    return (
        <div className={ `menu ${isOpen ? "open" : ""}` }>

            <br/>

                <li className="navigation-option">
                    <strong><Link to={"/"} > Home </Link></strong>
                </li>

                <li className="navigation-option">
                    <strong><Link to={"/"} > Option 2 </Link></strong>
                </li>

                <li className="navigation-option">
                    <strong><Link to={"/"} > Option 3 </Link></strong>
                </li>

            <br/><br/>

            <button id="collapse-menu-button"
                    onClick={closeMenu}> &#xd7;
            </button>

        </div>
    );

}