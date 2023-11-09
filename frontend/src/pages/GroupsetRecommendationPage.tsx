import GroupsetRecommendationsComponent from "../components/GroupsetRecommendation.tsx";
import {GroupsetRecommendation} from "../components/Entities.ts";
import axios from "axios";
import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";


export default function GroupsetRecommendationsPage() {

    const { id } = useParams();

    const [groupsetRecommendation, setGroupsetRecommendation] = useState<GroupsetRecommendation>( );


    function getGroupsetRecommendations() {
        axios
            .get(`/api/recommendations/${id}`)
            .then((response) => {
                console.log(response)
                setGroupsetRecommendation(response.data);
            })
            .catch((reason) => {
                console.log(reason)
            })
    }

    useEffect(() => {
            getGroupsetRecommendations();
    }, []);



    return (
        <div>
            { groupsetRecommendation &&
                <GroupsetRecommendationsComponent
                    key={groupsetRecommendation.id}
                    groupsetRecommendation={groupsetRecommendation}
                />
            }
        </div>
    );
}