import './App.css'
import useGeolocation from './useGeolocation.tsx';
import germanyMap from "./assets/de.svg";
import cyclistGif from "./assets/ezgif.com-crop.gif";
import Footer from "./Footer.tsx";
import Header from "./Header.tsx"; // Stellen Sie sicher, dass Sie den richtigen Pfad angeben

export default function App() {

    const { location, determineGeolocation } = useGeolocation()




    return (
        <>
            <Header/>
            <div className="container">

                <div>
                    <div className="introductionBox">
                        <p>Welcome to GroupsetHero - your assistant for equipping your roadbike with the optimal groupset!</p>
                        <p>We want to help you ensure, you're always pedalling with ease, considering the terrain and elevation levels of your environment. :) </p>
                        <p>- Say goodbye to bobbing from too light gears as well as to breaking a sweat from grinding on too heavy ones. </p>
                    </div>
                </div>


                <div className="imageDiv">
                    <div>
                        <img id="de-map" src={germanyMap} alt="DE-Map" />
                    </div>
                    <div>
                        <img id="cyclist-gif" src={cyclistGif} alt="n.a."/>
                    </div>
                </div>


                <div>
                    <div>
                    {
                        location.loaded ?
                            (
                                <>

                                    <form>
                                        <label>
                                            <strong><input defaultValue={location.coordinates?.lat.toFixed(4)}/></strong>
                                        </label>
                                                <br/>
                                        <label>
                                            <strong><input defaultValue={location.coordinates?.lng.toFixed(4)}/></strong>
                                        </label>
                                                <br/>
                                        <div>
                                            <button>Submit and Continue</button>
                                        </div>
                                    </form>
                                </>
                            )
                        :
                            (
                                <button onClick={determineGeolocation}>Determine your current location</button>
                            )

                    }
                    </div>


                </div>
            </div>
            <Footer/>
        </>
    )
}