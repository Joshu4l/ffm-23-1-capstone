import {ChangeEvent, FormEvent, useState} from 'react';
import {UserlocationDTO} from "../components/Entities.ts";
import "./UserlocationForm.css";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import InstructionBox from "../components/InstructionBox.tsx";

export type UserlocationFormProps = {
    latitude: number,
    longitude: number
}

export default function UserlocationForm(props: UserlocationFormProps) {

    const [userlocation, setUserlocation] = useState<UserlocationDTO | undefined>()

    const [latitude, setLatitude] = useState<number>(props.latitude)
    const [longitude, setLongitude] = useState<number>(props.longitude)
    const [radiusInKm, setRadiusInKm] = useState<number>()
    const [areaDesignation, setAreaDesignation] = useState<string>("")
    const [userName, setUserName] = useState<string>("")

    const navigate = useNavigate(); // ALREADY WORKING -> misses Styling for the component itself

    function changeLatitude(event: ChangeEvent<HTMLInputElement>) {
        const newLatitude: number = Number(event.target.value)
        setLatitude(newLatitude);
    }
    function changeLongitude (event: ChangeEvent<HTMLInputElement>) {
        const newLongitude: number = Number(event.target.value)
        setLongitude(newLongitude);
    }
    function changeRadiusInKm (event: ChangeEvent<HTMLInputElement>) {
        const newRadius: number = Number(event.target.value);
        setRadiusInKm(newRadius);
    }
    function changeAreaDesignation (event: ChangeEvent<HTMLInputElement>) {
        const newAreaDesignation: string = event.target.value.toString()
        setAreaDesignation(newAreaDesignation)
    }
    function changeUserName (event: ChangeEvent<HTMLInputElement>) {
        const newUsername: string = event.target.value.toString()
        setUserName(newUsername)
    }

    function submitEditedUserlocation(event: FormEvent) {
        event.preventDefault()
        // Send the POST request here
        axios.post("/api/userlocations",
            {
                ...userlocation,
                latitude: latitude,
                longitude: longitude,
                radiusInKm: radiusInKm,
                areaDesignation: areaDesignation,
                userName: userName
            }
        )
            .then(response => {
                console.log(response.data);
                setUserlocation(response.data);
                const newLocationId = response.data.id; // extracting the resulting response-object's id
                navigate(`/userlocations/${newLocationId}`); // using the extracted id to navigate to the UserlocationDetails component
            })
            .catch(reason => {
                console.log(reason);
            });
    }

    return (
        <div id="userlocation-form-container">

            <p>There you go &#9989;</p>
            <br/>

            <form onSubmit={(event) => submitEditedUserlocation(event)}>
                <div className="label-input">
                    <label className="done"><strong>LAT. coordinate:</strong></label>
                    <input type="number"
                           value={props.latitude}
                           maxLength={25}
                           onChange={changeLatitude}
                           required
                    />
                </div>
                <div className="label-input">
                    <label className="done"><strong>LNG. coordinate:</strong></label>
                    <input type="number"
                           value={props.longitude}
                           maxLength={25}
                           onChange={changeLongitude}
                           required
                    />
                </div>
                <br/>

                <InstructionBox/>

                <div className="label-input">
                    <label className="open"><strong>Radius in km:</strong></label>
                    <input type="number" id="radius-input"
                           value={radiusInKm}
                           onChange={changeRadiusInKm}/>
                </div>
                <div className="label-input">
                    <label className="open"><strong>Designation of the area:</strong></label>
                    <input type="text" id="designation-input"
                           value={areaDesignation}
                           onChange={changeAreaDesignation}/>
                </div>
                <div className="label-input">
                    <label className="open"><strong>Your user name:</strong></label>
                    <input type="text" id="username-input"
                           value={userName}
                           onChange={changeUserName}/>
                </div>
                <br/>

                <button id="finish-and-create-button">Finish and create this user location</button>
            </form>

        </div>
    )
}