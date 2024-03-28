import * as turf from "@turf/turf"; 
const formatForTurf = (arr) => {
    const [first] = arr;
    return [...arr, first];
  };
export const str2Coordinate = (str = '') => str?.split(',')

export const formatLandCoordinate2Arr = (arr = []) => (
    arr.map(({
        latitude,
        longitude
    }) => ([
        latitude,
        longitude
    ]))
);

export const formatLandCoordinate = (arr = []) => (
    arr.map(({lat:latitude, lng:longitude}) => ({
        longitude,
        latitude
    }))
)

export const formatLandCoordinateOther = (arr = []) => (
    arr.map(([latitude, longitude]) => ({
        longitude,
        latitude
    }))
) 

const getCenterOfGravityPoint = (arr) => {
    let area = 0.0;
    let  Gx = 0.0;
    let  Gy = 0.0;
    for (let i = 1; i<arr.length; i++) {
        let iLat = arr[i % arr.length].lat;
        let iLng = arr[i % arr.length].lng;
        let nextLat = arr[i - 1].lat;
        let nextLng = arr[i - 1].lng;
        let temp = (iLat * nextLng - iLng * nextLat) / 2.0;
        area += temp; 
        Gx += temp * (iLat + nextLat) / 3.0;
        Gy += temp * (iLng + nextLng) / 3.0;
    }
    Gx = Gx / area;
    Gy = Gy / area;

    return [Gx, Gy];
} 

export const formatLandCoordinateCenter = (arr = []) => {
    return turf.centroid(turf.polygon([formatForTurf(arr.map(({ lat, lng }) => [lat, lng]))])).geometry.coordinates.join(',')
}

export const squareMeter2mu = (sm, precision = 2) => (sm * 3 / 2000).toFixed(precision);

// 不同，则返回true
export const diffLandCoordinate = (posArr, landCoordinateArr) => {
    const res = landCoordinateArr.some((item,index) => {
        const [lat, lng] = posArr[index] || [];
        return item.lat !== lat || item.lng !== lng
    })
    return res;
}