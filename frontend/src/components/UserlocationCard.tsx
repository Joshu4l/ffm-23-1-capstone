import {Userlocation} from "./Entities.ts";
import "./UserlocationCard.css"

import spreadMap from "../assets/spread-map.png"
import {Link} from "react-router-dom";

type UserlocationCardProps = {
    userlocation: Userlocation
}

export default function UserlocationCard (props: UserlocationCardProps) {

    return (
        <div className="container">

            <Link to={`/userlocations/${props.userlocation.id}`} className="userlocation-card">

                {/*<img id="spread-map" src={spreadMap} alt="location-img" />*/}

                <div className="userlocation-properties-div">
                    <div className="userlocation-attribute-div">
                        <span className="attribute-label">ID:</span>
                        <span className="attribute-value">{props.userlocation.id}</span>
                    </div>
                    <div className="userlocation-attribute-div">
                        <span className="attribute-label">Designation :</span>
                        <span className="attribute-value">{props.userlocation.areaDesignation}</span>
                    </div>
                    <div className="userlocation-attribute-div">
                        <span className="attribute-label">User:</span>
                        <span className="attribute-value">{props.userlocation.userName}</span>
                    </div>
                    <div className="userlocation-attribute-div">
                        <span className="attribute-label">Latitude:</span>
                        <span className="attribute-value">{props.userlocation.latitude.toFixed(4)}</span>
                    </div>
                    <div className="userlocation-attribute-div">
                        <span className="attribute-label">Longitude:</span>
                        <span className="attribute-value">{props.userlocation.longitude.toFixed(4)}</span>
                    </div>
                    <div className="userlocation-attribute-div">
                        <span className="attribute-label">Radius in km:</span>
                        <span className="attribute-value">{props.userlocation.radiusInKm}</span>
                    </div>
                    <div className="userlocation-attribute-div">
                        <span className="attribute-label">Avg. elevation in %:</span>
                        <span className="attribute-value">{props.userlocation.averageElevationInPercent.toFixed(2)}</span>
                    </div>
                </div>

            </Link>

        </div>
    )
}