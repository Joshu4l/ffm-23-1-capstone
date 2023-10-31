import {Userlocation} from "../components/Entities.ts";
import {useEffect, useState} from "react";
import axios from "axios";
import UserlocationDetails from "./UserlocationDetails.tsx";

type UserlocationGalleryProps = {
    userlocation: Userlocation
}

export default function UserlocationGallery (props: UserlocationGalleryProps) {


    const [userlocations, setUserlocations] = useState<Userlocation[]>([])


    useEffect(() => {
        fetchUserlocationData();
    }, []);


    function fetchUserlocationData() {
        axios.get("/api/userlocations")
            .then(response => {
                setUserlocations(response.data)
            })
            .catch(reason => {
                console.error(reason)
            })
    }

    return (

        <div className="userlocation-gallery">
            {userlocations.map(
                (userlocation) => (
                        <UserlocationDetails key={userlocation.id} userlocation={userlocation} />
                        )
                )
            }
        </div>
    )
}