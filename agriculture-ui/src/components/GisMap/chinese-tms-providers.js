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
                  // 智慧农业 lrx_cn@163.com
                  "8adfb91083d58b6439fcd1246d08682f",
                  // 天地图test lrx_cn@163.com
                  "ab0b28fb4efdd2ff5e34634a011b9f31",
                  // 智慧农业扩展_1 lrx_cn@163.com
                  "da74b53ec3c358a190ba6c025e018bbc",
                  // 智慧农业扩展_2 lrx_cn@163.com
                  "b94355a3653179023f90e7fbf9c1602a",
                  // 智慧农业扩展_3 lrx_cn@163.com
                  "641954230b23cace70dfd1137a1ba956",
                  // 智慧农业扩展_4 HIT_AI_SmartAgriculture_01@somemail.com
                  "db56dcef7a488a43c08b9eb4c4eea1a4",
                  // 智慧农业扩展_5 HIT_AI_SmartAgriculture_01@somemail.com
                  "1777637842367b22417541402f462d67",
                  // 智慧农业扩展_6 HIT_AI_SmartAgriculture_01@somemail.com
                  "46006562477bdea294e1d37d76971bcd",
                  // 智慧农业扩展_7 HIT_AI_SmartAgriculture_01@somemail.com
                  "9e4a264bfdd1c55d98350814c27d9aad",
                  // 智慧农业扩展_8 HIT_AI_SmartAgriculture_01@somemail.com
                  "ac3c82d54037eb268e44edab1dd3fce2",
                  // 原来提供的
                  "174705aebfe31b79b3587279e211cb9a"
          ],
          key: "174705aebfe31b79b3587279e211cb9a"
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