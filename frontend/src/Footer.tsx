import "./Footer.css"
import landscapeBanner from './assets/landscapeBanner.jpg';

export default function Footer () {
    return (
        <div className="image-container">
            <img className="image" src={landscapeBanner} alt="Footer Banner"/>
        </div>
    );
}
