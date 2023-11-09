import { useNavigate, useParams } from "react-router-dom";
import { ChangeEvent, FormEvent, useEffect, useState } from "react";
import { Userlocation } from "../components/Entities.ts";
import "./UserlocationDetails.css";
import spreadMap from "../assets/spread-map.png";
import axios from "axios";

export default function UserlocationDetails() {
    const { id } = useParams();
    const [userlocation, setUserlocation] = useState<Userlocation | undefined>(undefined);

    const [initialState, setInitialState] = useState<Userlocation | undefined>(undefined);
    const [isBeingEdited, setIsBeingEdited] = useState<boolean>(false);

    // Initialize state variables
    const [latitude, setLatitude] = useState<number>(0);
    const [longitude, setLongitude] = useState<number>(0);
    const [radiusInKm, setRadiusInKm] = useState<number>(0);
    const [areaDesignation, setAreaDesignation] = useState<string>("");


    const navigate = useNavigate();

    function changeAreaDesignation(event: ChangeEvent<HTMLInputElement>) {
        setAreaDesignation(event.target.value);
    }
    function changeLatitude(event: ChangeEvent<HTMLInputElement>) {
        setLatitude(Number(event.target.value));
    }
    function changeLongitude(event: ChangeEvent<HTMLInputElement>) {
        setLongitude(Number(event.target.value));
    }
    function changeRadiusInKm(event: ChangeEvent<HTMLInputElement>) {
        setRadiusInKm(Number(event.target.value));
    }

    function fetchUserlocationDataById(id: string) {
        axios
            .get(`/api/userlocations/${id}`)
            .then((response) => {
                setUserlocation(response.data);
                setLatitude(response.data?.latitude || 0);
                setLongitude(response.data?.longitude || 0);
                setRadiusInKm(response.data?.radiusInKm || 0);
                setAreaDesignation(response.data?.areaDesignation || "");
                setInitialState(response.data);
            })
            .catch((reason) => {
                console.error(reason);
            });
    }

    function deleteUserlocationById(id: string) {
        axios
            .delete(`/api/userlocations/${id}`)
            .then(() => {
                navigate("/userlocations");
            })
            .catch((reason) => {
                console.log(reason);
            });
    }

    function toggleEditStatus() {
        setIsBeingEdited((prevIsBeingEdited) => !prevIsBeingEdited);
    }

    function cancelEdit() {
        // Revert to the initial state
        setAreaDesignation(initialState?.areaDesignation ?? "");
        setLatitude(initialState?.latitude ?? 0);
        setLongitude(initialState?.longitude ?? 0);
        setRadiusInKm(initialState?.radiusInKm ?? 0);
        setIsBeingEdited(false);
    }

    function updateUserlocationById(event: FormEvent) {
        event.preventDefault();
        axios
            .put(`/api/userlocations/${id}`, {
                ...userlocation,
                latitude: latitude,
                longitude: longitude,
                radiusInKm: radiusInKm,
                areaDesignation: areaDesignation,
                // refrain from sending userName and averageElevationInPercent at this point
            })
            .then((response) => {
                setUserlocation(response.data);
                setInitialState(response.data);
                setIsBeingEdited(false);
            })
            .catch((reason) => {
                console.log(reason);
            });
    }

    function requestGroupsetRecommendations() {
        axios
            .post(`/api/recommendations/calculate`,
                {
                    userlocationId: id,
                    averageElevationInPercent: userlocation?.averageElevationInPercent
                }
            )
            .then((response) => {
                const recommendationId = response.data.id
                navigate(`/groupset-recommendations/${recommendationId}`)
            })
    }

    useEffect(() => {
        if (id) {
            fetchUserlocationDataById(id);
        }
    }, [id]);

    return (
        <div id="edit-container">

            {isBeingEdited ? (
                // Display scenario for IS being edited
                <form onSubmit={updateUserlocationById}>
                    <p id="display-of-resulting-id-and-user">
                        <h3>Userlocation id: {userlocation?.id}</h3>
                        Belonging to user: {userlocation?.userName}
                    </p>
                    <p>
                        These are the credentials of your individual location. Based on the
                        information you're providing below, we are aiming to recommend the
                        perfect groupset for your road bike.
                    </p>
                    <br />

                    <div className="label-input">
                        <label>area designation</label>
                        <input
                            type="text"
                            value={areaDesignation}
                            onChange={changeAreaDesignation}
                        />
                    </div>
                    <div className="label-input">
                        <label>latitude</label>
                        <input
                            type="number"
                            step="0.0001"
                            value={latitude}
                            onChange={changeLatitude}
                        />
                    </div>
                    <div className="label-input">
                        <label>longitude</label>
                        <input
                            type="number"
                            step="0.0001"
                            value={longitude}
                            onChange={changeLongitude}
                        />
                    </div>
                    <div className="label-input">
                        <label>radius in km</label>
                        <input
                            type="number"
                            value={radiusInKm}
                            onChange={changeRadiusInKm}
                        />
                    </div>
                    <br />

                    <img id="spread-map-img" src={spreadMap} alt="spread-map" />
                    <br />
                    <br />

                    <button
                        id="cancel-userlocation-update-button"
                        type="button"
                        onClick={cancelEdit}
                    >
                        Cancel
                    </button>

                    <button id="save-userlocation-button">Save</button>
                </form>
            )
            :
            (
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
                    <button id="get-recommendations-button" type="button"
                        onClick={
                            () => {requestGroupsetRecommendations()}
                        }
                    >
                        &#x1F6B4; Get groupset recommendations!
                    </button>
                </div>
            </>
            )}
    </div>)
}
