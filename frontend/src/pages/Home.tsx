import {useNavigate} from "react-router-dom";
import cyclistGif from "../assets/cyclist.gif";
import locationPin from "../assets/location-pin.png";
import {LocationData} from "../custom-hooks/useGeolocation.tsx";

// PROPS
type HomeProps = {
    location: LocationData
    determineGeolocation: () => void
}


// FUNCTION
export default function Home(props: HomeProps) {

    // STATE
    const navigate = useNavigate();


    return (
        <div>
            {props.location.loaded ? (
                <>
                    <div className="imageDiv">
                        <img id="cyclist-gif" src={cyclistGif} alt="n.a." />
                        <img id="location-pin" src={locationPin} alt="DE-Map" />
                    </div>
                    <form>
                        <label><strong><input defaultValue={props.location.coordinates?.lat} /></strong></label>
                        <label><strong><input defaultValue={props.location.coordinates?.lng} /></strong></label>
                    </form>

                    <button onClick={() => navigate("/userlocation")} >
                        Submit to create my user location &rarr;
                    </button>
                </>
            ) : (
                <button onClick={props.determineGeolocation}>Start determining your current location &#x1F4CC;</button>
            )}
        </div>
    );
}