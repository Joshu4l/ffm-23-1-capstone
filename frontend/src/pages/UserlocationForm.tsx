//PROPS
export type UserlocationProps = {
    latitude: number,
    longitude: number
}


// FUNCTION
export default function UserlocationForm(props: UserlocationProps) {

    return (
        <div>

            Dies ist meine UserlocationForm Component
            <p>Latitude: {props.latitude}</p>
            <p>Longitude: {props.longitude}</p>

        </div>
    );
}