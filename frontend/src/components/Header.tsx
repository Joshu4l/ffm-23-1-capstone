import "./Header.css"
import sprocketSvg from "../assets/sprocket.svg";
import menuSvg from "../assets/burger-menu.svg";

export default function Header () {
    return (
        <div className="navbar">
            <img id="burger-menu-svg" src={menuSvg} alt=""/>
            <h2>GroupsetHero</h2> <img id="sprocket-svg" src={sprocketSvg} alt="sprocket image"/>
        </div>
    );
}