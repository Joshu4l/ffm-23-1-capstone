import {useNavigate, useParams} from "react-router-dom";
import { useEffect, useState } from "react";
import { Userlocation } from "../components/Entities.ts";
import "./UserlocationDetails.css"
import spreadMap from "../assets/spread-map.png"
import axios from "axios";


export default function UserlocationDetails() {

    const { id } = useParams();
    const [userlocation, setUserlocation] = useState<Userlocation>();
    const navigate = useNavigate()


    function fetchUserlocationDataById(id: string) {
        axios.get("/api/userlocations/" + id)
            .then(response => {
                setUserlocation(response.data);
            })
            .catch(reason => {
                console.error(reason);
            });
    }


    function deleteUserlocationById(id: string) {
        axios.delete(`/api/userlocations/${id}`)
            .then(() => {
                navigate("/userlocations")
            })
            /*            .then(() => {
                            fetchMovieData()
                        })*/
            .catch(
                (reason) => {console.log(reason)}
            )
    }


    useEffect(() => {
        if (id) {
            fetchUserlocationDataById(id)
        }
    }, [id]); // Ensuring the request is sent again once the id changes




    return (
        <div id="edit-container">

            <form>
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
                    <input type="text" value={userlocation?.areaDesignation} style={{ width: '18px' }} />
                </div>

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


                <img id="spread-map-img" src={spreadMap} alt="spread-map"/>


                <div id="elevation-information">
                    <p><strong>Calculated average elevation of your area in percent:</strong></p>
                    <p id="calculated-average-elevation"><strong>{userlocation?.averageElevationInPercent.toFixed(2)}</strong></p>
                </div>

                <div>
{/*                    <button id="delete-userlocation-button" type="button">
                        &#xd7; Delete this location
                        <Link to={"/userlocations"} onClick={() => props.deleteFunction(userlocation!.id)}>
                            Userlocation Gallery
                        </Link>
                    </button>*/}


                    <button
                        id="delete-userlocation-button"
                        type="button"
                        onClick={() => {
                            deleteUserlocationById(userlocation!.id);
                            // Führen Sie die Navigation zur Userlocation Gallery durch
                            navigate('/userlocations');
                        }}
                    >
                        &#xd7; Delete this location
                    </button>


                    <button id="edit-userlocation-button" type="button">
                        <span role="img" aria-label="Edit this userlocation">
                        ✏️
                        </span> Edit this userlocation
                    </button>

                    <button id="get-recommendations-button">
                        &#x1F6B4; Get groupset recommendations!
                    </button>
                </div>
            </form>

        </div>
    );
}
