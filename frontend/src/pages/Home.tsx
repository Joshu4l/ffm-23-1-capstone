import {useNavigate} from "react-router-dom";
import cyclistGif from "../assets/cyclist.gif";
import locationPin from "../assets/location-pin.png";
import {LocationData} from "../custom-hooks/useGeolocation.tsx";
import "./Home.css"

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
                        <img id="location-pin" src={locationPin} alt="DE-Map" />
                    </div>

                        <br/>

                        <form className="initial-form-container">
                            <div className="label-input">
                                <label><strong>LAT. coordinate:</strong></label>
                                <input type="number" id="latitude" defaultValue={props.location.coordinates?.lat} />
                            </div>
                            <div className="label-input">
                                <label><strong>LNG. coordinate:</strong></label>
                                <input type="number" id="longitude" defaultValue={props.location.coordinates?.lng} />
                            </div>
                        </form>

                        <br/>

                    <button onClick={() => navigate("/userlocation")} >
                        Submit to configure my user location &rarr;
                    </button>
                </>

            ) : (

                <>
                    <div className="imageDiv">
                        <img id="cyclist-gif" src={cyclistGif} alt="n.a." />
                    </div>
                    <br/>

                    <button onClick={props.determineGeolocation}>Start determining my current location &#x1F4CC;</button>
                </>
            )}
        </div>
    );
}