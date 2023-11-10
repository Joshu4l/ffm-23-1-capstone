import React, { useState } from 'react';
import axios from 'axios';
import {GroupsetRecommendation} from "../components/Entities.ts";

export default function SliderEdit( {groupsetRecommendation} : { groupsetRecommendation: GroupsetRecommendation }) {

/*
    const [cranksetDimensions, setCranksetDimensions] = useState<number[]>(groupsetRecommendation.cranksetDimensions)
    const [cranksetDimensionsLabel, setCranksetDimensionsLabel] = useState<string>(groupsetRecommendation.cranksetDimensions.toString)

    const [largestSprocket, setLargestSprocket] = useState<number>(groupsetRecommendation.largestSprocket)
    const [largestSprocketLabel, setLargestSprocketLabel] = useState<string>(groupsetRecommendation.largestSprocket.toString)
*/


    const [wheelDiameter, setWheelDiameter] = useState<number>(28)
    const [wheelDiameterLabel, setWheelDiameterLabel] = useState<string>('28" inches')




 /*   const handleCranksetSliderChange = (event: React.ChangeEvent<HTMLInputElement>) => {

        const newValue: number[] = parseInt(event.target.value, 10);
        setCranksetDimensions(newValue);

        let newCranksetDimensions = '';

        if (newValue === 1) {
            newCranksetDimensions = '50, 34';
        } else if (newValue === 2) {
            newCranksetDimensions = '52, 36';
        } else if (newValue === [53, 39]) {
            newCranksetDimensions = '53, 39';
        }
        setCranksetDimensionsLabel(newCranksetDimensions)

    };*/



    const handleWheelDiameterSliderChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const newValue = parseInt(event.target.value, 10);
        setWheelDiameter(newValue);

        let newWheelDiameterLabel = '';

        if (newValue === 26) {
            newWheelDiameterLabel = '26" inches';
        } else if (newValue === 28) {
            newWheelDiameterLabel = '28" inches';
        }
        setWheelDiameterLabel(newWheelDiameterLabel);
    };


    const sendSliderValueToAPI = () => {
        // Send the current sliderValue to the backend
        axios.post("/api/recommendations/calculate",
            {
                    wheelDiameter: wheelDiameter
                }
        )
        .then((response) => {
            console.log('Daten erfolgreich gesendet:', response.data);
        })

        .catch((error) => {
            console.error('Fehler beim Senden der Daten:', error);
        });
    };


    const customSteps = [28, 32, 34, 36, 40]



    // Verweise auf den Slider und die Anzeige
    const slider = document.getElementById("largestSprocketRange");

    // Event-Handler für Änderungen am Slider
    slider.addEventListener("input", () => {
        // Finde den nächsten Schritt basierend auf dem Slider-Wert
        const value = parseInt(slider.value);
        const closestStep = customSteps.reduce(
                (prev, curr) => Math.abs(curr - value) < Math.abs(prev - value) ? curr : prev
        );

        // Setze den Slider-Wert auf den nächsten Schritt
        slider.value = closestStep;

        return (
            <div className="slidecontainer">

                {/*
              <div>
                    <input
                        className="slider"
                        id="cranksetRange"
                        type="range"
                        min="53, 39"
                        max="3"
                        step="1"
                        value={cranksetDimensions}
                        onChange={handleCranksetSliderChange}
                        style={{ width: '30%' }}
                    />
                    <p>Value <span id="demo">{cranksetDimensions}: {cranksetDimensionsLabel}</span></p>
                <div/>
                */}

                <div>
                    <input
                        className="slider"
                        id="largestSprocketRange"
                        type="range" min="28" max="40" step="1"
                        value={wheelDiameter}
                        onChange={handleWheelDiameterSliderChange}
                        style={{ width: '30%' }}

                    />
                    <p><span id="demo">{wheelDiameterLabel}</span></p>
                </div>



                <div>
                    <input
                        className="slider"
                        id="wheelDiameterRange"
                        type="range" min="26" max="28" step="2"
                        value={wheelDiameter}
                        onChange={handleWheelDiameterSliderChange}
                        style={{ width: '30%' }}
                    />
                    <p><span id="demo">{wheelDiameterLabel}</span></p>
                </div>




                <button onClick={sendSliderValueToAPI}>Send to API</button>

                </div>
 /*           </div>*/
        );
    }
    )
}