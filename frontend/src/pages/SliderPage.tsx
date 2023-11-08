import React, { useState } from 'react';
import axios from 'axios';

export default function SliderPage() {
    const [sliderValue, setSliderValue] = useState<number>(2);
    const [cranksetDimensions, setCranksetDimensions] = useState<string>('');

    const handleSliderChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const newValue = parseInt(event.target.value, 10);
        setSliderValue(newValue);

        let newCranksetDimensions = '';
        if (newValue === 1) {
            newCranksetDimensions = 'standard';
        } else if (newValue === 2) {
            newCranksetDimensions = 'semi-compact';
        } else if (newValue === 3) {
            newCranksetDimensions = 'compact';
        }

        setCranksetDimensions(newCranksetDimensions);
        console.log(newCranksetDimensions)
    };

    const sendSliderValueToAPI = () => {
        // Send the current sliderValue to the backend
        axios.post("/api/recommendations",
            {slidervalue: sliderValue}
        )

            .then((response) => {
                console.log('Daten erfolgreich gesendet:', response.data);
            })

            .catch((error) => {
                console.error('Fehler beim Senden der Daten:', error);
            });

        axios.post("/api/recommendations", {
            slidervalue: sliderValue,
        })

    };

        return (
            <div>
                <h3>Our crankset recommendation based on your location</h3>
                <div className="slidecontainer">
                    <input
                        className="slider"
                        id="myRange"
                        type="range"
                        min="1"
                        max="3"
                        value={sliderValue}
                        onChange={handleSliderChange}
                        style={{ width: '30%' }}
                    />
                    <p>Value <span id="demo">{sliderValue}: {cranksetDimensions}</span></p>
                    <button onClick={sendSliderValueToAPI}>Send to API</button>
                </div>
            </div>
        );
    }
