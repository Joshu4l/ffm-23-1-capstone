import {Userlocation} from "../components/Entities.ts";
import UserlocationCard from "../components/UserlocationCard.tsx";

type UserlocationGalleryProps = {
    userlocations: Userlocation[]
}

export default function UserlocationGallery (props: UserlocationGalleryProps) {

    return (

        <div className="userlocation-gallery">
            {
                props.userlocations.map(
                    (userlocation) => (<UserlocationCard userlocation={userlocation}/>)
                )
            }
        </div>
    )
}