import { nextTick, reactive, ref } from 'vue'

// import Provider from "./chinese-tms-providers";
import Provider from "@/utils/chinatmsproviders";
import * as turf from "@turf/turf";
import L from "leaflet";
import "@geoman-io/leaflet-geoman-free";
import '@geoman-io/leaflet-geoman-free/dist/leaflet-geoman.css';
import { CONFIG } from "./config";

/**
 * 创建地图
 * @param {HTMLElement} mapContainer 
 * @param {*} mapConfig 
 */

export function useGisMap() {
  const state = reactive({
    map: ref()
  })
  const createMap = (mapContainer, mapConfig = {}, center = [CONFIG.CHINA_CENTER.lat, CONFIG.CHINA_CENTER.lng]) => {
    const { controls, customStyle, ...base } = mapConfig
    Provider(L);
    if(state.map) return
    state.map = L.map(mapContainer, {
      center: new L.LatLng(center[0], center[1]),
      ...base
    });
    L.tileLayer
      .chinaProvider("TianDiTu.Satellite.Map", {
        maxZoom: 20,
        minZoom: 5,
      })
      .addTo(state.map);
    state.map.pm.setLang(base.locale)
    controls && state.map.pm.addControls(controls);
    customStyle && state.map.pm.setPathOptions(...customStyle);
  }
  const mapPlus = () => {
    state.map.zoomIn();
  }
  const mapMinus = () => {
    state.map.zoomOut();
  }
  const mapFresh = (freshFn) => {
    freshFn()
  }
  const mapLocate = (locateFn) => {
    locateFn()
  }
  const drawPolygon = () => {
    // 启用绘制模式
    state.map.pm.enableDraw('Polygon', {
      snappable: true,
      snapDistance: 20
    });
  }

  return {
    state,
    createMap,
    mapPlus,
    mapMinus,
    mapFresh,
    mapLocate,
    drawPolygon,
  }
}
