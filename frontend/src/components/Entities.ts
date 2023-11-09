export type UserlocationDTO = {
    latitude: number,
    longitude: number,
    radiusInKm: number,
    areaDesignation: string,
    userName: string
}

export type Userlocation = {
    id: string,
    latitude: number,
    longitude: number,
    radiusInKm: number,
    areaDesignation: string,
    userName: string,
    averageElevationInPercent: number
}

export type GroupsetRecommendation = {

    id: string,
    userlocationId: string,
    areaDesignation: string,
    averageElevationInPercent: number,
    elevationInterpretation: string,
    cranksetDimensions: number[],
    smallestSprocket: number,
    largestSprocket: number

}