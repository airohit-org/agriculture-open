import Provider from "@/utils/chinatmsproviders";
import L from "leaflet";
import AutoGraticule from "./L.AutoGraticule";
import { THOUSAND, UNDEFINED } from '@/constants';
import {CONFIG} from '@/views/land/land/help.js'

export const createPolygon = (pos, config, id, handleClick) => {
  const p = new L.Polygon(pos, config);
  p._id = id;
  handleClick && p.on("click", handleClick);
  return p;
};

export const createGrid = ({bounds}) => {
  return new AutoGraticule({
    bounds: L.latLngBounds(bounds),
    redraw: 'moveend',
    minDistance: 10,
    lineStyle: {
        stroke: true,
        color: '#76f6d0',
        opacity: .4,
        weight: 1,
        interactive: false
    }
  })
};

export const zoomAndCenterExactArr = (map, pos, arr = [], padding = [200, 200]) => {
  if (!arr || !pos || !map) {
    return
  }
  arr.forEach(layer => { layer.setStyle({ opacity: 0, fillOpacity: 0 }) });
  map.flyToBounds(pos, { duration: CONFIG.ZOOM_CENTER_ANIMATION_DURATION, padding });
  setTimeout(() => {
    arr.forEach(layer => { layer.setStyle({ opacity: 1, fillOpacity: CONFIG.FILL_OPACITY.NORMAL }) });
  }, CONFIG.ZOOM_CENTER_ANIMATION_DURATION * THOUSAND);
}

export const createMap = (mapContainer) => {
    Provider(L);
    let map = L.map(mapContainer, {
      center: new L.LatLng(CONFIG.CHINA_CENTER.lat, CONFIG.CHINA_CENTER.lng),
      zoom: 5,
      zoomControl: false,
      attributionControl: false,
      doubleClickZoom: false
    });
    L.tileLayer
      .chinaProvider("TianDiTu.Satellite.Map", {
        maxZoom: 20,
        minZoom: 5,
      })
      .addTo(map);
    return map;
  }

export const WEATHER_BOUDARY = [
  // 黑龙江
  // [42.901906, 117.883301],
  // [49.907689, 139.174805]
  // 全国
  // 73~135.5°E，12.15~54.2°N
  [12.15,73],
  [54.2, 135.5]
];

export const createImgOverlay = (map, bounds = WEATHER_BOUDARY, imgSrc = 'https://oss.airoteach.cn/90a2134105ce90eb548541bc22129b7d2766d7a83877d56622c345d73fa6863e.png', opacity = .5 ) => (
  L.imageOverlay(imgSrc, bounds, { opacity }).addTo(map)
)


const ICON_SIZE_WIDTH = 20;
const ICON_SIZE_HEIGHT = 23.6;

export const createMarkerIcon = (config) =>
  config.iconUrl && L.icon({
    iconSize: [ICON_SIZE_WIDTH, ICON_SIZE_HEIGHT],
    ...config
  });