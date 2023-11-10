import {GroupsetRecommendation} from "./Entities.ts";
import "./GroupsetRecommendation.css"

export default function GroupsetRecommendationsComponent({ groupsetRecommendation } : { groupsetRecommendation: GroupsetRecommendation }) {

    return (
        <div className="recomm">

            <p id="display-of-recommendation">
                <h3>Recommendation:  {groupsetRecommendation.id}</h3>
            </p>
            <p id="display-of-userlocation-id">
                Belonging to userlocation {groupsetRecommendation.userlocationId}
            </p>
            <br/>


            <p className="informative">
                Below you can find our groupset recommendation considering the characteristics of the area you specified:
            </p>
            <br/><br/>

            <div className="recommendation-div">
                <div className="recommendation-attribute-div">
                    <span className="attribute-label">Avg. elevation of your area in %: </span>
                    <span className="attribute-value">{groupsetRecommendation.averageElevationInPercent.toFixed(2)}</span>
                </div>
                <div className="recommendation-attribute-div">
                    <span className="attribute-label">Interpretation of the elevation: </span>
                    <span className="attribute-value">{groupsetRecommendation.elevationInterpretation}</span>
                </div>

                <hr></hr>

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

        </div>
    );
}


