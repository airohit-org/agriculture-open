import * as esri from "esri-leaflet";
import Provider from "@/utils/chinatmsproviders";
import { CONFIG as LandConfig } from "../land/land/help";
export { zoomAndCenterExactArr, getLatlngByPopup } from "../land/land/help";
import SideProvider from "@/utils/sideBySide/sideBySide";

export const createMap = (
  mapContainer,
  mapConfig,
  center = [LandConfig.CHINA_CENTER.lat, LandConfig.CHINA_CENTER.lng]
) => {
  Provider(L);
  let map = L.map(mapContainer, {
    center: new L.LatLng(center[0], center[1]),
    zoom: 5,
    zoomControl: false,
    attributionControl: false,
    doubleClickZoom: false,
    // crs: L.CRS.EPSG4326,
    ...mapConfig,
  });
  L.tileLayer
    .chinaProvider("TianDiTu.Satellite.Map", {
      // .chinaProvider("GoogleTiandi.Satellite.Map2", {
      maxZoom: 18,
      minZoom: 5,
      useCache: true,
      crossOrigin: true,
    })
    .addTo(map);
  return map;
};

export const CONFIG = {
  findOneUrl:
    "/0/query?geometryType=esriGeometryPoint&returnGeometry=true&f=json&outFields=*&geometry=",
};

export const addLandLayer = (map, landUrl) => {
  const layer = esri.dynamicMapLayer({
    url: landUrl,
    opacity: 0.8,
    f: "json",
  });
  map.addLayer(layer);
  return layer;
};

export const addOneGrowthLayer = (map, url) => {
  if(!map || !url) {
    return null;
  }
  let dynamicLayer = esri.dynamicMapLayer({
    url,
    opacity: 0.8,
    f: "json",
    pane: map.createPane()
  });
  dynamicLayer.addTo(map);
  return dynamicLayer;
};

export const createSide = (map, a, b) => {
  SideProvider(L);
  const side = L.control.sideBySide([a], [b]);
  side.addTo(map);
  return side;
};

// export const addGrowthLayer = (map) => {
//   const layer = esri.dynamicMapLayer({
//     url: CONFIG.growthUrl,
//     opacity: 0.5,
//     f: "json",
//   });
//   map.addLayer(layer);
//   return layer;
// };

export const createPolygon = (pos, config) => new L.Polygon(pos, config);

export const drawOnClick = (map, landUrl, callBack = () => {}) => {
  map.on("click", (e) => {
    const { lat, lng } = e.latlng || {};
    const arr = [lng, lat];
    fetch(`${landUrl}${CONFIG.findOneUrl}${arr.join(",")}`)
      .then((res) => res.json())
      .then((res) => {
        const [obj] = res.features || [];
        const pos = obj?.geometry?.rings?.[0].map(([a, b]) => [b, a]);
        if (pos) {
          callBack(pos, obj.attributes);
        }
      });
  });
};
