import { ipLocate } from "@/api/land/land.js";
import { formatLands } from '@/store/modules/landMap/help';
import { getStore, setStore, STORE_KEY, uniqueSimArr } from "@/utils";
import Provider from "@/utils/chinatmsproviders";
import { THOUSAND, UNDEFINED } from '@/constants';
import { squareMeter2mu } from "@/utils/latlng.js";
import * as turf from "@turf/turf";
import L from "leaflet";

export const deviceList = [

]
export const device = [

]

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

export const zoomAndCenter = (map, pos, layerGroup) => {
  if (!layerGroup || !pos || !map) {
    return
  }
  layerGroup.eachLayer(layer => layer.setStyle({ opacity: 0, fillOpacity: 0 }))
  map.flyToBounds(pos, { duration: CONFIG.ZOOM_CENTER_ANIMATION_DURATION, padding: [100, 100] });
  setTimeout(() => {
    layerGroup?.eachLayer(layer => { layer.setStyle({ opacity: 1, fillOpacity: CONFIG.FILL_OPACITY.NORMAL }) })
  }, CONFIG.ZOOM_CENTER_ANIMATION_DURATION * THOUSAND);
}

export const createPolygon = (pos, config, id, handleClick, hasHover = true) => {
  const p = new L.Polygon(pos, config);
  p._id = id;
  hasHover && p.on("mouseover", () => {
    p.setStyle({ fillOpacity: CONFIG.FILL_OPACITY.HIGHLIGHT });
  });
  hasHover && p.on("mouseout", () => {
    p.setStyle({ fillOpacity: CONFIG.FILL_OPACITY.NORMAL });
  });
  handleClick && p.on("click", handleClick);
  return p;
};

const ICON_SIZE_WIDTH = 35.22;
const ICON_SIZE_HEIGHT = 48.24;

export const createMarkerIcon = (config) =>
  config.iconUrl && L.icon({
    iconSize: [ICON_SIZE_WIDTH, ICON_SIZE_HEIGHT],
    ...config
  });

const ANCHOR_UNIT = 10;

export const iconAnchors = [
  // 0个不理会
  [],
  // 只有一个 居中
  [
    [0, 0]
  ],
  // 有2个时, 左右结构
  [
    [2 * ANCHOR_UNIT * .7, 0],
    [-2 * ANCHOR_UNIT * .7, 0]
  ],
  // 有3个时，左下，右下，正中上
  [
    [-2 * ANCHOR_UNIT * .7, -2 * ANCHOR_UNIT * .7],
    [2 * ANCHOR_UNIT * .7, -2 * ANCHOR_UNIT * .7],
    [0, 2 * ANCHOR_UNIT * .5],
  ],
  // 有4个时，在4个角落
  [
    [0, 2 * ANCHOR_UNIT],
    [0, -2 * ANCHOR_UNIT],
    [2 * ANCHOR_UNIT, 0],
    [-2 * ANCHOR_UNIT, 0],
  ]
];

export const correctCenter = ([start, end]) => [
  start + ICON_SIZE_WIDTH / 2,
  end + ICON_SIZE_WIDTH / 2
]

export const createMarkerIcons = (config, index, total) => {
  return createMarkerIcon({
    ...config,
    iconAnchor: correctCenter(iconAnchors[total][index % iconAnchors.length])
  })
}

export const createTextIcon = (content, config) =>
  L.divIcon({
    html: content,//marker标注
    className: 'my-div-icon',
    ...config
  });


export const getLatlngByPopup = (map) => {
  map.on("click", ({ latlng }) => {
    L.popup().setLatLng(latlng).setContent(latlng.toString()).openOn(map);
  });
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

export const CONFIG = {
  LAYERS: {
    TextLayer: {
      label: "病情监测站",
      value: 0,
      activeImgSrc: 'https://oss.airoteach.cn/942df573acb55a2f9508e343a45a63c5f10f04ed739edd47485ae1ae1f14a394.png',
      imgSrc: 'https://oss.airoteach.cn/825a94cfcaf5cae94ad89b2b68d23b52bed17571d5499ca81e25d925fcab1541.png',
    },
    AreaLayer: {
      label: "气象站",
      value: 1,
      imgSrc: 'https://oss.airoteach.cn/bb705aab0e33857f34191916cb70f8f3b6f4d5ce453dbddad01dae40e2d3ca48.png',
      activeImgSrc: 'https://oss.airoteach.cn/852c4728a2f8ccbe685fb69cf7a1413bbb911bcb88cdf6e176e0d8051d322ec6.png',
    },
    IconLayer: {
      label: "土壤墒情站",
      value: 2,
      imgSrc: 'https://oss.airoteach.cn/21c0867315d64fd5ff6215ed62285a81e125c875af5365ebf3724cd86f5c93d4.png',
      activeImgSrc: 'https://oss.airoteach.cn/7a8cb4c87e0c880591f98d6dd24bc20116572459636c741fe69702711d4ff75c.png',
    },
    FarmLayer: {
      label: "智慧环控设备",
      value: 3,
      activeImgSrc: 'https://oss.airoteach.cn/700cd80377e43de7b80d602bdd939f112e2f55580da07bdd767611317a7bb25e.png',
      imgSrc: 'https://oss.airoteach.cn/b8fdae25c87a3a6d28a1cc132e91809b7a3da2c1f60f023c5f3926210c98d85f.png',
    },
    DeviceLayer: {
      label: "虫情监测站",
      value: 4,
      imgSrc: 'https://oss.airoteach.cn/646bf6d207db171d43bdb65c70579419d278923f867f58b48286de891c3fa9f4.png',
      activeImgSrc: 'https://oss.airoteach.cn/fcdeda5c0fd9e2ece754c4ecd966dc24835b5e5e52f569eaef4b80234ce7cb43.png',
    },
    VideoLayer: {
      label: "视频监控",
      value: 5,
      activeImgSrc: 'https://oss.airoteach.cn/70fe171f1df47049c011bdc8e0127f3cea0fd9aadc0166c7cfc875bf8011efad.png',
      imgSrc: 'https://oss.airoteach.cn/e9f07e3a8826bbdc610a934783ddd209af1997810349d9d41fd64dafdaaecc3a.png',
    },
  },
  CHINA_CENTER: {
    lat: 34.269701,
    lng: 108.942146
  },
  MAX_AREA: 50000,
  FILL_OPACITY: {
    NORMAL: .2,
    HIGHLIGHT: .7
  },
  ZOOM_CENTER_ANIMATION_DURATION: .25
};

export const getStatuslist = () => [
  {
    label: '气象站',
    value: CONFIG.LAYERS.AreaLayer.value,
    imgSrc: CONFIG.LAYERS.AreaLayer.imgSrc,
    activeImgSrc: CONFIG.LAYERS.AreaLayer.activeImgSrc,
  },
  {
    label: '土壤墒情站',
    value: CONFIG.LAYERS.IconLayer.value,
    imgSrc: CONFIG.LAYERS.IconLayer.imgSrc,
    activeImgSrc: CONFIG.LAYERS.IconLayer.activeImgSrc,
  },
  {
    label: '病情监测站',
    value: CONFIG.LAYERS.TextLayer.value,
    imgSrc: CONFIG.LAYERS.TextLayer.imgSrc,
    activeImgSrc: CONFIG.LAYERS.TextLayer.activeImgSrc,
  },
  {
    label: '智慧环控设备',
    value: CONFIG.LAYERS.FarmLayer.value,
    imgSrc: CONFIG.LAYERS.FarmLayer.imgSrc,
    activeImgSrc: CONFIG.LAYERS.FarmLayer.activeImgSrc,
  },
  // {
  //   label: '视频监控',
  //   value: CONFIG.LAYERS.VideoLayer.value,
  //   imgSrc: CONFIG.LAYERS.VideoLayer.imgSrc,
  //   activeImgSrc: CONFIG.LAYERS.VideoLayer.activeImgSrc,
  // },
  {
    label: '虫情监测站',
    value: CONFIG.LAYERS.DeviceLayer.value,
    imgSrc: CONFIG.LAYERS.DeviceLayer.imgSrc,
    activeImgSrc: CONFIG.LAYERS.DeviceLayer.activeImgSrc,
    zIndex: 201,
  },
]

export const getStatusFromCache = () => {
  const res = getStore(STORE_KEY.MAP_SHOW_STATUS_SELECT, [
    CONFIG.LAYERS.AreaLayer.value,
    CONFIG.LAYERS.IconLayer.value,
    CONFIG.LAYERS.TextLayer.value,
  ]);
  return uniqueSimArr([].concat(res).concat(CONFIG.LAYERS.AreaLayer.value))
}

export const setStatusStore = (arr) => {
  setStore(STORE_KEY.MAP_SHOW_STATUS_SELECT, arr)
}

export const COLOR_LIST = [
  '#C8FF52',
  '#FF1F25',
  '#3400FF',
  '#52F5FF',
  '#FFE602',
  '#FF9922'
];

export const FORM_TYPE_ENUM = {
  INPUT: 0,
  COLOR: 1,
  CROP: 2
};

export const TOP_BTN_SHOW_ENUM = {
  DRAWING: 0,
  NO_DRAWING: 1,
  EDITING: 2,
  FARM: 3
}

export const findLayerInGroupByParam = (layerGroup, target, key) => {
  let res;
  layerGroup?.eachLayer((layer) => {
    if (layer[key] === target) {
      res = layer
    }
  });
  return res
}

export const calcArea = (layer) => {
  const [[first, ...arr]] = layer?._latlngs || [];
  let pointArr = [first, ...arr, first].map(({ lat, lng }) => [lat, lng]);
  const polygon = turf.polygon([pointArr]);
  const area = turf.area(polygon);

  return squareMeter2mu(area, 2);
}

export const mockForm = {
  area: "100",
  contacts: "刘七八",
  crops: "土豆",
  cropsType: "1",
  landName: "地块5",
  status: 0,
  tel: "15712345678",
};

export const CROPS_CODE = {
  maize: '0',
  paddy: '1',
  soybean: '2',
  crop: '3',
}

export const OTHER_CROP = {
  img: '/images/crops/crop.png',
  label: '其他',
  val: '-1'
};


export const OTHER_CROP_TYPE = {
  label: '其他',
  value: '-1'
};

export const formatCropsList = (raiseCrops = []) => raiseCrops.map(({
  imageUrl: img,
  cropsName: label,
  code: val
}) => ({
  img: img || 'https://avatars.githubusercontent.com/u/12810740?s=200&v=4',
  label,
  val,
})).concat(OTHER_CROP)


export const formatSearchOptions = (listMap = {}, value) => {
  const base = listMap?.[value]?.cropsVarietiesList?.map(({
    code: value,
    cropsVarietiesName: label
  }) => ({
    value,
    label
  }))

  // if (base?.concat) {
  //   return base.concat(OTHER_CROP_TYPE)
  // }
  return base;
}

export const calcCropsCreateReqVOList = (cropsCreateReqVOList) => (
  cropsCreateReqVOList.map(item => {
    const { crops, cropsType } = item || {};
    const _cropsType = (crops === OTHER_CROP.val ? OTHER_CROP_TYPE.value : cropsType);
    return {
      ...item,
      cropsIsOther: ~~(crops === OTHER_CROP.val),
      cropsType: (crops === OTHER_CROP.val ? OTHER_CROP_TYPE.value : cropsType),
      cropsTypeIsOther: ~~(_cropsType === OTHER_CROP_TYPE.value),
    }
  })
)

export const fomatCreateLand = (form) => {
  const { cropsCreateReqVOList } = form || {};
  return {
    ...form,
    cropsCreateReqVOList: calcCropsCreateReqVOList(cropsCreateReqVOList)
  }
}

export const formatForTurf = (arr) => {
  const [first] = arr;
  return [...arr, first];
};

export const getLatLng = (layer) => {
  const [latlng = []] = layer.getLatLngs();
  return latlng.map(({ lat, lng }) => [lat, lng]);
};

export const isOverBoundary = (container, polygon) => {
  const pllC = turf.polygon([formatForTurf(getLatLng(container))]);
  const pllP = turf.polygon([formatForTurf(getLatLng(polygon))]);

  const difference = turf.difference(pllP, pllC);
  return !!difference;
};


export const formatLandList = (list = []) => {
  return formatLands(list);
}

export const iplocateAndFit = (map) => (
  ipLocate()
    .then((res) => {
      res &&
        map.flyToBounds(res, { duration: 2, padding: [100, 100] });
    })
)

export const TOOLS_CONFIG = [
  {
    icon: 'https://oss.airoteach.cn/0fb5b3b1a292bc6c329e62ff30a9f66c60e50b80e4c69a34731eaf68af4291f9.png',
    type: 'plus'
  },
  {
    icon: 'https://oss.airoteach.cn/9ae3685f0f5adc33aabbd24ae2be7719c6ad01d768f73397c5f547f9fb26dabd.png',
    type: 'minus'
  },
  {
    icon: 'https://oss.airoteach.cn/3752f40f2a899ae4a4c29560946cde10445f74e0d1b48eaf481bf2c29b68a93f.png',
    type: 'locate'
  },
  {
    icon: 'https://oss.airoteach.cn/d940de1c1aad61627cd3013dc8a4cbee6a69ec7d41b93133beb8b6c59b3e7073.png',
    type: 'fresh'
  },
]
export const keyTitle = {
  "倾角": "https://oss.airoteach.cn/7b78e0bd2d8e5cd72f74595911260741ea28d0318ce7b081e9327df2e9987cdc.png",
  "土壤氮含量": "https://oss.airoteach.cn/6061103f0c183fd6ee6ab96589d2367180651451238b1d7fc422ee0cd9a92400.png",
  "土壤EC值": "https://oss.airoteach.cn/c6d346e2ccc5ba70cc274f4173c0d2fde3d5a72b7e69dea40776f06372b4803c.png",
  "土壤钾含量": "https://oss.airoteach.cn/6756a17a5b827a33de06859456af0cc6c5912a769801dfafa4b6833c517ee06a.png",
  "土壤磷含量": "https://oss.airoteach.cn/18b04eab89e2794e02b88e75309df85c21bc640e5c6b4388135c42e979b41bf4.png",
  "土壤PH值": "https://oss.airoteach.cn/c6fbe89366dc0d5a5afaed42a9c1d5b5930623af9e0688204cbdaf5ecdb5c7bf.png",
  "土壤表层湿度": "https://oss.airoteach.cn/0440c94bee79508aafc50c81d0d817c40b488531330bbe7c83cddcae7a5e5247.png",
  "土壤湿度第一层": "https://oss.airoteach.cn/4d3b97f1c987cc887566dcedab793d68a8ad3a63b93f9e7985f9fc2e317707b0.png",
  "土壤湿度第二层": "https://oss.airoteach.cn/f9ad40ac7c553479d64df77e7418e821fbeb6b506c8a58bd702639de9222cf29.png",
  "土壤湿度第三层": "https://oss.airoteach.cn/c9494be3a57f1411dc7d9e70f39eb215f973e1d1d37c53bd64d21e7088859a9b.png",
  "土壤表层温度": "https://oss.airoteach.cn/4e9365f588d5fcf75edfa70c18bc910a245007d84677283ce448c62ccec7e015.png",
  "土壤温度第一层": "https://oss.airoteach.cn/57e244920b2174d2cac1a25dee876c2a611613cc4a2b346aa7b304b9c2287f3a.png",
  "土壤温度第二层": "https://oss.airoteach.cn/67dc34f6ef32f5412d2c10a2d518e4910f380e1ff617e1e9f52eb4d016bc46d3.png",
  "土壤温度第三层": "https://oss.airoteach.cn/52ae875254bc58ec5c484a650bef5149c45f415784391cc7b9dd1edc1cd5717c.png",
  "累积降雨量": "https://oss.airoteach.cn/6c788ebd4541832265432e08c1bb04c4cdf5c31287b31a317aab040891906c14.png",
  "co2含量": "https://oss.airoteach.cn/db86a42ea332e0e8f53e5488f8ddbafec6bb47521c067442a0fb7c2cbfd20d7d.png",
  "蒸发": "https://oss.airoteach.cn/a2de09fedc92c2b49976eb4cc1c1763e9113c53043b98fc6cdb18d70fe278b1f.png",
  "光照强度": "https://oss.airoteach.cn/1241d5bb90df18f2c0337b63d75d9e698d019316f7d2e45f883e66ebca1ae3af.png",
  "噪声": "https://oss.airoteach.cn/4ae5a9d606c1f14a2b3a10e2a91857fc38e2dc25d32833335b3504af41f86faf.png",
  "光合有效辐射": "https://oss.airoteach.cn/6bdb501751e20e5d38e8c9202f503c552f641c4adffbb1d8964ee9326fb41668.png",
  "pm10": "https://oss.airoteach.cn/769f18fbbce65f90bbc6948542b8571b01d852b1d73e6694eb1b8be073332728.png",
  "pm2.5": "https://oss.airoteach.cn/ffbea08caa8569da8367eaeb313ed5789180fd106bdb7013e75a82a99e8a0d96.png",
  "气压": "https://oss.airoteach.cn/d9fe53d9984ef3989c76d2b932cbfb2fe1eeda4243c7ef1da0087ab06de25e5e.png",
  "瞬时降水量": "https://oss.airoteach.cn/f35c9ede52dcae88c1b681dd0c378a66bd31eb8f56496b3c3129e9b404447f23.png",
  "湿度": "https://oss.airoteach.cn/6d42fe24e846d0052a8fcb8d55344db1789dd69363509991c1817dd85fcb9b98.png",
  "土壤表层电导率": "https://oss.airoteach.cn/7d7c5508b535d6ad6206f223db90b1e6dfa70ec4713aa44365ced19f2a9160eb.png",
  "温度": "https://oss.airoteach.cn/afd64977fb43a5d43adefe8e8a229b5009f72ecd0c1e308ceb62ca37ceb26f66.png",
  "当前降水量": "https://oss.airoteach.cn/144076d10eaba97972cba7bda0a37bce33f0530d0c49fa2fd085b45c828eb8e1.png",
  "太阳总辐射": "https://oss.airoteach.cn/37a4e0d2a3bd21c81141683e0c435df68b981bb8fd4de77a55a68b7bf41ee02f.png",
  "紫外线强度": "https://oss.airoteach.cn/4a1b70232f9d7badc30831bb14bbc4bb9c859decb322b3aca6e6540de7b97bb9.png",
  "风力": "https://oss.airoteach.cn/b9bf12c36c2e75a32a8269748e0f2355555535de265cc4d6ae641efbc76cf303.png",
  "风向": "https://oss.airoteach.cn/fdc253bd75a33b586670448bf62fe459fbe830765a566093e3a8ee47af2e6499.png",
  "风速": "https://oss.airoteach.cn/d5324b1c554b0a422989976d08b398aefc48432ea5c38a501e5bc9fb2bff9b67.png",
  "昨日降水量": "https://oss.airoteach.cn/acaeba22eef9e749873511bf8a295906e64fd505ca2370115c95d60982a014c9.png"
}

export const deviceIcon = {
  '温度': '',
  '湿度': '',
  '风力': '',
  '风速': '',
  '风向': '',
  '土温': '',
  '土湿': '',
  'EC': '',
  '噪声': '',
  'PM10': '',
  'PM2.5': '',
  '大气压': '',
  '光照': '',
  '雨雪': '',
  '紫外线指数': '',
  '总辐射': '',
  '光合有效辐射': '',
  '累计雨量': '',
  '瞬时雨量': '',
  '当前雨量': '',
  '昨日雨量': '',
  '二氧化碳': '',
  '继电器1': '',
  '继电器2': '',
  '继电器3': '',
  '继电器4': '',
  '继电器5': '',
  '继电器6': '',
  '继电器7': '',
  '继电器8': '',
  '降雨状态': '',
  '杀虫仓温度': '',
  '光照度': '',
  '烘干仓温度': '',
  'CO2': '',
  '光照度': '',
}