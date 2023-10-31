import {ChangeEvent, FormEvent, useState} from 'react';
import {UserlocationDTO} from "../components/Entities.ts";
import "./UserlocationForm.css";
import axios from "axios";
import {useNavigate} from "react-router-dom";

export type UserlocationProps = {
    latitude: number,
    longitude: number
}

export default function UserlocationForm(props: UserlocationProps) {

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
                setUserlocation(response.data)
                navigate("/userlocations") // ALREADY WORKING -> misses styling for the component itself
            })
            .catch(reason => {
                console.log(reason);
            });
    }

    return (
        <div id="userlocation-form-container">
            <form onSubmit={(event) => submitEditedUserlocation(event)}>
                <div className="label-input">
                    <label><strong>LAT. coordinate:</strong></label>
                    <input type="number"
                           value={props.latitude}
                           maxLength={25}
                           onChange={changeLatitude}/>
                </div>
                <div className="label-input">
                    <label><strong>LNG. coordinate:</strong></label>
                    <input type="number"
                           value={props.longitude}
                           maxLength={25}
                           onChange={changeLongitude}/>
                </div>
                <div className="label-input">
                    <label><strong>Radius in km:</strong></label>
                    <input type="number" id="radius-input"
                           value={radiusInKm}
                           onChange={changeRadiusInKm}/>
                </div>
                <div className="label-input">
                    <label><strong>Designation of the area:</strong></label>
                    <input type="text" id="designation-input"
                           value={areaDesignation}
                           onChange={changeAreaDesignation}/>
                </div>
                <div className="label-input">
                    <label><strong>Your user name:</strong></label>
                    <input type="text" id="username-input"
                           value={userName}
                           onChange={changeUserName}/>
                </div>
                <button>Finish and create this user location</button>
            </form>

        </div>
    )
}
