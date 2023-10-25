import { useState } from "react";


// TYPE PREPARATION
export type LocationData = {
    loaded: boolean
    coordinates: { lat: number, lng: number }
    errorMessage: string
}


// ORIGINAL FUNCTION
const useGeolocation = () => {

    // USE STATE
    const [location, setLocation] = useState<LocationData>(
        {
                    loaded: false,
                    errorMessage: "",
                    coordinates: {lat: 0.0000, lng: 0.0000}
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
        )
    }

    // FAILURE SCENARIO
    const onError = (error: GeolocationPositionError) => {
        setLocation({
            loaded: true,
            errorMessage: error.message || "Geolocation not supported!",
            coordinates: {lat: 0.0000, lng: 0.0000}
        })
    }


    // COPING WITH THE ACTUAL BUTTON CLICK
    const determineGeolocation = () => {

        if (!("geolocation" in navigator)) {
            const error: GeolocationPositionError = {
                code: 2,
                message: "Geolocation not supported",
                PERMISSION_DENIED: 1,
                POSITION_UNAVAILABLE: 2,
                TIMEOUT: 3
            }
            onError(error)

        } else { navigator.geolocation.getCurrentPosition(onSuccess, onError) }
    }
    return { location, determineGeolocation }


}
export default useGeolocation;
