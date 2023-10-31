import {Userlocation} from "../components/Entities.ts";

type UserlocationDetailsProps = {
    userlocation: Userlocation
}

export default function UserlocationDetails (props: UserlocationDetailsProps) {

    return (
        <div>
            {props.userlocation.id}
            {props.userlocation.latitude}
            {props.userlocation.longitude}
            {props.userlocation.radiusInKm}
            {props.userlocation.areaDesignation}
            {props.userlocation.userName}
            {props.userlocation.averageElevationInPercent}
        </div>
    )
}