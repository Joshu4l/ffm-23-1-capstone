import landscapeBanner from '../assets/landscapeBanner.jpg';
import "./Footer.css"

export default function Footer () {
    return (
        <div className="footer-component">
            <img id="landscape-banner" src={landscapeBanner} alt="Footer Banner"/>
        </div>
    );
}
