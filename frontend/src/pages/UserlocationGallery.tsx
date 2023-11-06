import {Userlocation} from "../components/Entities.ts";
import UserlocationCard from "../components/UserlocationCard.tsx";
import {useEffect, useState} from "react";
import axios from "axios";


export default function UserlocationGallery () {

    const [userlocations, setUserlocations] = useState<Userlocation[]>([])

    // AXIOS
    function fetchUserlocationData() {
        axios.get("/api/userlocations")
            .then(response => {
                setUserlocations(response.data)
            })
            .catch(reason => {
                console.error(reason)
            })
    }

    // RENDER BEHAVIOUR
    useEffect(() => {
        fetchUserlocationData();
    }, [userlocations]);


    return (

        <div className="userlocation-gallery">
            {
                userlocations.map(
                    (userlocation) => (<UserlocationCard userlocation={userlocation}/>)
                )
            }
        </div>
    )
}