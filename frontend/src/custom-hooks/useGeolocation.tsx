import { useState } from "react";

// TYPE PREPARATION
type LocationData = {
    loaded: boolean
    coordinates?: { lat: number, lng: number }
    errorMessage: string
}


// ORIGINAL FUNCTION
const useGeolocation = () => {

    // USE STATE
    const [location, setLocation] = useState<LocationData>(
        {
                    loaded: false,
                    errorMessage: "",
                   }
        );


    // SUCCESS SCENARIO
    const onSuccess = (location: GeolocationPosition) => {
        setLocation(
        {
                loaded: true,
                coordinates: { lat: location.coords.latitude, lng: location.coords.longitude },
                errorMessage: ""
                }
        );
    };


    // FAILURE SCENARIO
    const onError = (error: GeolocationPositionError) => {
        setLocation({
            loaded: true,
            errorMessage: error.message || "Geolocation not supported!",
        });
    };


    // COPING WITH THE ACTUAL BUTTON CLICK
    const determineGeolocation = () => {

        if (!("geolocation" in navigator)) {
            const error: GeolocationPositionError = {
                code: 2, // You can use any appropriate code from GeolocationPositionError
                message: "Geolocation not supported",
                PERMISSION_DENIED: 1, // Optional, you can set it to 1 or leave it out
                POSITION_UNAVAILABLE: 2, // Optional, you can set it to 2 or leave it out
                TIMEOUT: 3 // Optional, you can set it to 3 or leave it out
            };
            onError(error);
        } else {
            navigator.geolocation.getCurrentPosition(onSuccess, onError);
        }
    };

    return { location, determineGeolocation };
};

export default useGeolocation;
