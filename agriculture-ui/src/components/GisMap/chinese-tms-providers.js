export const rand = (min = 0, max = 1) => (
  Math.round(Math.random() * (max - min) + min)
)

const getKeyFromKeys = (aks) => {
  const akProxy = new Proxy(aks, {
      get(target, prop){
          if (prop === 'ak') {
              return target[rand(0, target.length - 1)]
          }
          return target[prop]
      }
  })
  return akProxy.ak;
};


export default function providers(L) {
  L.TileLayer.ChinaProvider = L.TileLayer.extend({
      initialize: function (type, options) { // (type, Object)
          var providers = L.TileLayer.ChinaProvider.providers;
          var parts = type.split('.');
          var providerName = parts[0];
          var mapName = parts[1];
          var mapType = parts[2];
          var url = providers[providerName][mapName][mapType];
          options.subdomains = providers[providerName].Subdomains;

          let key = options.key || providers[providerName].key;
          const keys = options.keys || providers[providerName].keys;
          
          options.key = keys ? getKeyFromKeys(keys) : key;
          L.TileLayer.prototype.initialize.call(this, url, options);
      }
  });
  L.TileLayer.ChinaProvider.providers = {
      TianDiTu: {
          Normal: {
              Map: "https://t{s}.tianditu.gov.cn/vec_w/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=vec&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk={key}",
              Annotion: "https://t{s}.tianditu.gov.cn/cva_w/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=cva&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk={key}"
          },
          Satellite: {
              Map: "https://t{s}.tianditu.gov.cn/img_w/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=img&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk={key}",
              Annotion: "https://t{s}.tianditu.gov.cn/cia_w/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=cia&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk={key}"
          },
          Terrain: {
              Map: "https://t{s}.tianditu.gov.cn/ter_w/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=ter&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk={key}",
              Annotion: "https://t{s}.tianditu.gov.cn/cta_w/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=cta&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk={key}"
          },
          Subdomains: ['0', '1', '2', '3', '4', '5', '6', '7'],
          keys: [
                  // 需要申请天地图服务key，配置在此处，可配置多个
          ],
          key: "" // 使用的天地图服务key
      },
      GaoDe: {
          Normal: {
              Map: 'http://webrd0{s}.is.autonavi.com/appmaptile?lang=zh_cn&size=1&scale=1&style=8&x={x}&y={y}&z={z}'
          },
          Satellite: {
              Map: 'http://webst0{s}.is.autonavi.com/appmaptile?style=6&x={x}&y={y}&z={z}',
              Annotion: 'http://webst0{s}.is.autonavi.com/appmaptile?style=8&x={x}&y={y}&z={z}'
          },
          Subdomains: ["1", "2", "3", "4"]
      },
      Google: {
          Normal: {
              Map: "http://www.google.cn/maps/vt?lyrs=m@189&gl=cn&x={x}&y={y}&z={z}"
          },
          Satellite: {
              Map: "http://www.google.cn/maps/vt?lyrs=s@189&gl=cn&x={x}&y={y}&z={z}"
          },
          Subdomains: []
      },
      GoogleTiandi: {
          Satellite: {
              MapAndAnnotion: "https://gac-geo.googlecnapps.cn/maps/vt?lyrs=s,m&gl=CN&x={x}&y={y}&z={z}",
              Map2: "https://gac-geo.googlecnapps.cn/maps/vt?lyrs=s&x={x}&y={y}&z={z}",
              MapAndAnnotion2: "https://gac-geo.googlecnapps.cn/maps/vt?lyrs=s&gl=CN&x={x}&y={y}&z={z}",
              Map: "https://gac-geo.googlecnapps.cn/maps/vt?lyrs=s,m&x={x}&y={y}&z={z}"
          },
          Subdomains: []
      },
      Geoq: {
          Normal: {
              Map: "http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer/tile/{z}/{y}/{x}",
              PurplishBlue: "http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineStreetPurplishBlue/MapServer/tile/{z}/{y}/{x}",
              Gray: "http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineStreetGray/MapServer/tile/{z}/{y}/{x}",
              Warm: "http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineStreetWarm/MapServer/tile/{z}/{y}/{x}",
          },
          Theme: {
              Hydro: "http://thematic.geoq.cn/arcgis/rest/services/ThematicMaps/WorldHydroMap/MapServer/tile/{z}/{y}/{x}"
          },
          Subdomains: []
      },
      OSM: {
          Normal: {
              Map: "http://{s}.tile.osm.org/{z}/{x}/{y}.png",
          },
          Subdomains: ['a', 'b', 'c']
      }

  };
  L.tileLayer.chinaProvider = function (type, options) {
      return new L.TileLayer.ChinaProvider(type, options);
  };
}