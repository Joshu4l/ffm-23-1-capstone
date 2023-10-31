import {Userlocation} from "../components/Entities.ts";
import UserlocationDetails from "./UserlocationDetails.tsx";

type UserlocationGalleryProps = {
    userlocations: Userlocation[]
}

export default function UserlocationGallery (props: UserlocationGalleryProps) {




    return (

        <div className="userlocation-gallery">
            {props.userlocations.map(
                (userlocation) => (
                        <UserlocationDetails key={userlocation.id} userlocation={userlocation} />
                        )
                )
            }
        </div>
    )
}