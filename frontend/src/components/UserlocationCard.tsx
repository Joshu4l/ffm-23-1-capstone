import {Userlocation} from "./Entities.ts";

type UserlocationCardProps = {
    userlocation: Userlocation
}

export default function UserlocationCard (props: UserlocationCardProps) {

    return (
        <div id="edit-container">

            These are the credentials of your individual location.
            Based on the information you're providing below, we are aiming to recommend the perfect groupset for your road bike.

            <form>
                <p>{props.userlocation.id}</p>
                <div className="label-input">
                    <label>area designation</label>
                    <input type="string"
                           value={props.userlocation.areaDesignation}
                           style={{ width: '18px' }}
                    />
                </div><br/>


                <div className="label-input">
                    <label>latitude</label>
                    <input type="number"
                           value={props.userlocation.latitude.toFixed(4)}
                    />
                </div>

                <div className="label-input">
                    <label>longitude</label>
                    <input type="number"
                           value={props.userlocation.longitude.toFixed(4)}
                    />
                </div>

                <div className="label-input">
                    <label>radius in km</label>
                    <input type="number"
                           value={props.userlocation.radiusInKm}
                    />
                </div><br/>


                <div id="elevation-information">
                    <p><strong>Average elevation of your area in percent:</strong></p>
                    <p id="calculated-average-elevation"><strong>{props.userlocation.averageElevationInPercent.toFixed(2)}</strong></p>
                </div>

                <button>Save and update this location</button>
            </form>

        </div>
    )
}