import {useNavigate, useParams} from "react-router-dom";
import {ChangeEvent, FormEvent, useEffect, useState} from "react";
import { Userlocation } from "../components/Entities.ts";
import "./UserlocationDetails.css"
import spreadMap from "../assets/spread-map.png"
import axios from "axios";


export default function UserlocationDetails() {

    const { id } = useParams();
    const [userlocation, setUserlocation] = useState<Userlocation>();

    const [latitude, setLatitude] = useState<number>()
    const [longitude, setLongitude] = useState<number>()
    const [radiusInKm, setRadiusInKm] = useState<number>()
    const [areaDesignation, setAreaDesignation] = useState<string>("")

    const [isBeingEdited, setIsBeingEdited] = useState<boolean>(false)
    const navigate= useNavigate()

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


    function fetchUserlocationDataById(id: string) {
        axios.get("/api/userlocations/" + id)
            .then(
                response => {setUserlocation(response.data)}
            )
            .catch(
                reason => {console.error(reason)}
            )
    }

    function deleteUserlocationById(id: string) {
        axios.delete(`/api/userlocations/${id}`)
            .then(
                () => {navigate("/userlocations")}
            )
            .catch(
                (reason) => {console.log(reason)}
            )
    }

    function toggleEditStatus() {
        setIsBeingEdited(true)
    }
    function updateUserlocationById(event: FormEvent, id: string) {
        event.preventDefault()
        axios.put(`/api/userlocations/${id}`,
                {
                    ...userlocation,
                    latitude: latitude,
                    longitude: longitude,
                    radiusInKm: radiusInKm,
                    areaDesignation: areaDesignation,
                    // refrain from sending userName and averageElevationInPercent at this point
                }
            )
            .then(
                (response) => {setUserlocation(response.data)},
                () => {setIsBeingEdited(false)}
            )
            .catch(
                (reason) => {console.log(reason)}
            )
    }

    useEffect(
        () => {
            if (id) {
                fetchUserlocationDataById(id)
            }
        },
        [id, updateUserlocationById] // Ensuring the request is sent again once the id changes
    );



    return (
        <div id="edit-container">

            {isBeingEdited ?

                /*display scenario for IS being edited*/
                <form onSubmit={(event) => updateUserlocationById(event, userlocation?.id)}>
                    <p id="display-of-resulting-id-and-user">
                        <h3>Userlocation id:  {userlocation?.id}</h3>
                        Belonging to user: {userlocation?.userName}
                    </p>
                    <p>
                        These are the credentials of your individual location.
                        Based on the information you're providing below, we are aiming to recommend the perfect groupset for your road bike.
                    </p>
                    <br/>

                    <div className="label-input">
                        <label>area designation</label>
                        <input type="text"
                               value={userlocation?.areaDesignation}
                               onChange={changeAreaDesignation}
                        />
                    </div>
                    <div className="label-input">
                        <label>latitude</label>
                        <input type="number"
                               value={userlocation?.latitude.toFixed(4)}
                               onChange={changeLatitude}
                        />
                    </div>
                    <div className="label-input">
                        <label>longitude</label>
                        <input type="number"
                               value={userlocation?.longitude.toFixed(4)}
                               onChange={changeLongitude}
                        />
                    </div>
                    <div className="label-input">
                        <label>radius in km</label>
                        <input type="number"
                               value={userlocation?.radiusInKm}
                               onChange={changeRadiusInKm}
                        />
                    </div><br/>

                    <img id="spread-map-img" src={spreadMap} alt="spread-map"/>
                    <br/><br/>


                        <button id="save-userlocation-button">
                            Save
                        </button>

                </form>

            :

                /*display scenario for NOT being edited*/
                <>
                    <p id="display-of-resulting-id-and-user">
                        <h3>Userlocation id:  {userlocation?.id}</h3>
                        Belonging to user: {userlocation?.userName}
                    </p>
                    <p>
                        These are the credentials of your individual location.
                        Based on the information you're providing below, we are aiming to recommend the perfect groupset for your road bike.
                    </p>
                    <br/>

                    <div className="userlocation-attribute-div">
                        <span className="attribute-label">Designation: </span>
                        <span className="attribute-value">{userlocation?.areaDesignation}</span>
                    </div>
                    <div className="userlocation-attribute-div">
                        <span className="attribute-label">Latitude:</span>
                        <span className="attribute-value">{userlocation?.latitude.toFixed(4)}</span>
                    </div>
                    <div className="userlocation-attribute-div">
                        <span className="attribute-label">Longitude:</span>
                        <span className="attribute-value">{userlocation?.longitude.toFixed(4)}</span>
                    </div>
                    <div className="userlocation-attribute-div">
                        <span className="attribute-label">Radius in km:</span>
                        <span className="attribute-value">{userlocation?.radiusInKm}</span>
                    </div>
                    <div className="userlocation-attribute-div">
                        <span className="attribute-label">Average elevation in %:</span>
                        <span className="attribute-value">{userlocation?.averageElevationInPercent.toFixed(2)}</span>
                    </div>
                    <br/><br/>

                    <div>
                        <button id="delete-userlocation-button" type="button"
                                onClick={
                                    () => {
                                        deleteUserlocationById(userlocation!.id);
                                        navigate('/userlocations');
                                    }
                                }
                        >
                            &#xd7; Delete this location
                        </button>


                        <button id="edit-userlocation-button" type="button"
                            onClick={
                                () => {toggleEditStatus()}
                            }
                        >
                            <span role="img" aria-label="Edit this userlocation">
                                ✏️
                            </span> Edit this userlocation
                        </button>


                        <button id="get-recommendations-button">
                            &#x1F6B4; Get groupset recommendations!
                        </button>
                    </div>
                </>
            }
    </div>)
}
