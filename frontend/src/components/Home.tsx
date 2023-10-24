import useGeolocation from "../custom-hooks/useGeolocation.tsx";
import {useNavigate} from "react-router-dom";
import cyclistGif from "../assets/cyclist.gif";
import locationPin from "../assets/location-pin.png";

export default function Home() {
    const { location, determineGeolocation } = useGeolocation();
    const navigate = useNavigate();
    return (
        <div className="returnContainer">
            {location.loaded ? (
                <>
                    <div className="imageDiv">
                        <img id="cyclist-gif" src={cyclistGif} alt="n.a." />
                        <img id="location-pin" src={locationPin} alt="DE-Map" />
                    </div>
                    <form>
                        <label>
                            <strong><input defaultValue={location.coordinates?.lat.toFixed(4)} /></strong>
                        </label>
                        <label>
                            <strong><input defaultValue={location.coordinates?.lng.toFixed(4)} /></strong>
                        </label>
                    </form><br/>

                    <div>
                        <button onClick={() => navigate("/userlocation")}>Submit to create my user location &rarr;</button>
                    </div>

                </>)
            :
                (
                    <button onClick={determineGeolocation}>Start determining your current location &#x1F4CC;</button>
                )
            }
        </div>);
}