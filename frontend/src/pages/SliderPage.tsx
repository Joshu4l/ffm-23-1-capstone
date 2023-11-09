import React, { useState } from 'react';
import axios from 'axios';

export default function SliderPage() {
    const [cranksetCategoryValue, setCranksetCategoryValue] = useState<number>(3);
    const [cranksetDimensionLabel, setCranksetDimensionLabel] = useState<string>('');

    const [wheelDiameterValue, setWheelDiameterValue] = useState<number>()
    const [wheelDiameterLabel, setWheelDiameterLabel] = useState<string>('')

    const handleCranksetSliderChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const newValue = parseInt(event.target.value, 10);
        setCranksetCategoryValue(newValue);

        let newCranksetDimensions = 'compact';

        if (newValue === 1) {
            newCranksetDimensions = 'standard';
        } else if (newValue === 2) {
            newCranksetDimensions = 'semi-compact';
        } else if (newValue === 3) {
            newCranksetDimensions = 'compact';
        }
        setCranksetDimensionLabel(newCranksetDimensions);
    };


    const handleWheelDiameterSliderChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const newValue = parseInt(event.target.value, 10);
        setWheelDiameterValue(newValue);

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
        axios.post("/api/recommendations",
            {
                    cranksetCategoryValue: cranksetCategoryValue,
                    wheelDiameterValue: wheelDiameterValue
                }
        )

            .then((response) => {
                console.log('Daten erfolgreich gesendet:', response.data);
            })

            .catch((error) => {
                console.error('Fehler beim Senden der Daten:', error);
            });
    };

        return (
            <div  className="slidecontainer">
                <h3>Our crankset recommendation based on your location</h3>

                <div>
                    <input
                        className="slider"
                        id="cranksetRange"
                        type="range"
                        min="1"
                        max="3"
                        value={cranksetCategoryValue}
                        onChange={handleCranksetSliderChange}
                        style={{ width: '30%' }}
                    />
                    <p>Value <span id="demo">{cranksetCategoryValue}: {cranksetDimensionLabel}</span></p>
                <div/>

                <div>
                    <input
                        className="slider"
                        id="wheelDiameterRange"
                        type="range"
                        min="26"
                        max="28"
                        step="2"
                        value={wheelDiameterValue}
                        onChange={handleWheelDiameterSliderChange}
                        style={{ width: '30%' }}
                    />
                    <p><span id="demo">{wheelDiameterLabel}</span></p>
                </div>


                <button onClick={sendSliderValueToAPI}>Send to API</button>

                </div>
            </div>
        );
    }
