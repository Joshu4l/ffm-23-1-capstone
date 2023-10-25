import {ChangeEvent, FormEvent, useEffect, useState} from 'react';
import axios from "axios";
import {Userlocation} from "./Entities.ts";

export type UserlocationProps = {
    latitude: number,
    longitude: number
}

export default function UserlocationForm(props: UserlocationProps) {

    const [userlocation, setUserlocation] = useState<Userlocation | undefined>()
    const [latitude, setLatitude] = useState<number>(props.latitude)
    const [longitude, setLongitude] = useState<number>(props.longitude)
    const [radius, setRadius] = useState<number>(0)
    const [areaDesignation, setAreaDesignation] = useState<string>("")
    const [userName, setUserName] = useState<string>("")


    function changeLatitude(event: ChangeEvent<HTMLInputElement>) {
        const newLatitude: number = Number(event.target.value)
        setLatitude(newLatitude);
    }
    function changeLongitude (event: ChangeEvent<HTMLInputElement>) {
        const newLongitude: number = Number(event.target.value)
        setLongitude(newLongitude);
    }
    function changeRadius (event: ChangeEvent<HTMLInputElement>) {
        const newRadius: number = Number(event.target.value);
        setRadius(newRadius);
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
                radius: radius,
                areaDesignation: areaDesignation,
                userName: userName
                }
            )
            .then(response => {
                console.log(response.data);
                setUserlocation(response.data)
            })
            .catch(reason => {
                console.log(reason);
            });
    }

    useEffect(() =>{

        }, []
    )

    return (
        <div id="userlocation-form-container">
            <form onSubmit={(event) => submitEditedUserlocation(event)}>
                <div className="label-input">
                    <label><strong>LAT. coordinate:</strong></label>
                    <input type="number"
                           defaultValue={props.latitude}
                           value={latitude}
                           onChange={changeLatitude}/>
                </div>
                <div className="label-input">
                    <label><strong>LNG. coordinate:</strong></label>
                    <input type="number" defaultValue={props.longitude}
                           value={longitude}
                           onChange={changeLongitude}/>
                </div>
                <div className="label-input">
                    <label><strong>Radius in km:</strong></label>
                    <input type="number" id="radius-input"
                           value={radius}
                           onChange={changeRadius}/>
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
                <button>Initialize Object</button>
            </form>

        </div>
    )
}
