import { useParams } from "react-router-dom";
import "./UserlocationDetails.css"
import { useEffect, useState } from "react";
import axios from "axios";
import { Userlocation } from "../components/Entities.ts";

export default function UserlocationDetails() {

    const { id } = useParams();
    const [userlocation, setUserlocation] = useState<Userlocation>();


    useEffect(() => {
        if (id) {
            fetchUserlocationDataById(id)
        }
    }, [id]); // Ensuring the request is sent again once the id changes


    function fetchUserlocationDataById(id: string) {
        axios.get("/api/userlocations/" + id)
            .then(response => {
                setUserlocation(response.data);
            })
            .catch(reason => {
                console.error(reason);
            });
    }

    return (
        <div id="edit-container">

            <form>
                <p id="display-of-resulting-id-and-user">
                    Userlocation id:  {userlocation?.id}
                <br/>
                    Belonging to user: {userlocation?.userName}
                </p>

                <p>
                    These are the credentials of your individual location.
                    Based on the information you're providing below, we are aiming to recommend the perfect groupset for your road bike.
                </p>

                    <br/>

                <div className="label-input">
                    <label>area designation</label>
                    <input type="text" value={userlocation?.areaDesignation} style={{ width: '18px' }} />
                </div><br/>

                <div className="label-input">
                    <label>latitude</label>
                    <input type="number" value={userlocation?.latitude.toFixed(4)} />
                </div>

                <div className="label-input">
                    <label>longitude</label>
                    <input type="number" value={userlocation?.longitude.toFixed(4)} />
                </div>

                <div className="label-input">
                    <label>radius in km</label>
                    <input type="number" value={userlocation?.radiusInKm} />
                </div><br/>

                <div id="elevation-information">
                    <p><strong>Average elevation of your area in percent:</strong></p>
                    <p id="calculated-average-elevation"><strong>{userlocation?.averageElevationInPercent.toFixed(2)}</strong></p>
                </div>

                    <br/><br/>

                <div>
                    <button id="edit-userlocation-button" type="button">Edit this userlocation</button>
                    <button>Get my groupset recommendations!</button>
                </div>
            </form>

        </div>
    );
}
