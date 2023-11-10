import {useNavigate} from "react-router-dom";
import cyclistGif from "../assets/cyclist.gif";
import locationPin from "../assets/location-pin.png";
import {LocationData} from "../custom-hooks/useGeolocation.tsx";
import "./Home.css"
import IntroductionBox from "../components/IntroductionBox.tsx";

// PROPS
type HomeProps = {
    location: LocationData
    determineGeolocation: () => void
}


// FUNCTION
export default function Home(props: HomeProps) {

    // NAVIGATION HOOK
    const navigate = useNavigate();

    return (
        <div>

            {props.location.loaded ? (

                <div id="auto-locate-container">

                    <div className="imageDiv">
                        <img id="location-pin" src={locationPin} alt="DE-Map" />
                    </div>

                    <p>Gotcha!</p>
                    <br/>

                    <form className="initial-form-container">
                        <div className="label-input">
                            <label><strong>LAT. coordinate:</strong></label>
                            <input type="number" id="latitude" defaultValue={props.location.coordinates?.lat.toFixed(4)} />
                        </div>
                        <div className="label-input">
                            <label><strong>LNG. coordinate:</strong></label>
                            <input type="number" id="longitude" defaultValue={props.location.coordinates?.lng.toFixed(4)} />
                        </div>
                    </form>
                    <br/>

                    <p id="explain-courses-of-action">
                        Satisfied with the auto-located coordinates?
                        Then go on by submitting them or manually edit them in the fields above!
                    </p>
                    <br/><br/>

                    <button className="standard-form-button" onClick={() => navigate("/userlocations/create")} >
                        Submit to configure my user location &rarr;
                    </button>

                </div>

            ) : (

                <>
                    <IntroductionBox/>

                    <div className="imageDiv">
                        <img id="cyclist-gif" src={cyclistGif} alt="n.a." />
                    </div>
                    <br/><br/>

                    <button className="standard-form-button" onClick={props.determineGeolocation}>Start determining my current location &#x1F4CC;</button>
                </>

            )
            }
        </div>
    );
}