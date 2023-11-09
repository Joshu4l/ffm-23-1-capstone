import {GroupsetRecommendation} from "./Entities.ts";
import "./GroupsetRecommendation.css"

export default function GroupsetRecommendationsComponent({ groupsetRecommendation }: { groupsetRecommendation: GroupsetRecommendation }) {

    return (
        <>
            <p id="display-of-recommendation-and-location-id">
                <h3>Recommendation id:  {groupsetRecommendation.id}</h3>
                Belonging to userlocation: {groupsetRecommendation.userlocationId} with designation {groupsetRecommendation.areaDesignation}
            </p>
            <p className="informative">
                Below you can find our groupset recommendation considering the characteristics of the area you specified:
            </p>
            <br/>

            <div className="recommendation-div">
                <div className="recommendation-attribute-div">
                    <span className="attribute-label">Average elevation of your area in percent: </span>
                    <span className="attribute-value">{groupsetRecommendation.averageElevationInPercent.toFixed(2)}</span>
                </div>
                <div className="recommendation-attribute-div">
                    <span className="attribute-label">Interpretation of the elevation value: </span>
                    <span className="attribute-value">{groupsetRecommendation.elevationInterpretation}</span>
                </div>
                <div className="recommendation-attribute-div">
                    <span className="attribute-label">Recommended crankset dimensions:</span>
                    <span className="attribute-value">{groupsetRecommendation.cranksetDimensions[0]} {groupsetRecommendation.cranksetDimensions[1]}</span>
                </div>
                <div className="recommendation-attribute-div">
                    <span className="attribute-label">Recommended smallest sprocket size:</span>
                    <span className="attribute-value">{groupsetRecommendation.smallestSprocket}</span>
                </div>
                <div className="recommendation-attribute-div">
                    <span className="attribute-label">Recommended largest sprocket size:</span>
                    <span className="attribute-value"> {groupsetRecommendation.largestSprocket}</span>
                </div>
                <div className="recommendation-attribute-div">
                    <span className="attribute-label">Resulting gear ratio:</span>
                    <span className="attribute-value"> {(groupsetRecommendation.cranksetDimensions[1] / groupsetRecommendation.largestSprocket).toFixed(2)}</span>
                </div>
            </div>
        </>
    );
}


