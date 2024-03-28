import { ipLocate } from "@/api/land/land.js";
import { formatLands } from '@/store/modules/landMap/help';
import { getStore, setStore, STORE_KEY, uniqueSimArr } from "@/utils";
import Provider from "@/utils/chinatmsproviders";
import { squareMeter2mu } from "@/utils/latlng.js";
import * as turf from "@turf/turf";
import L from "leaflet";

const UNDEFINED = void 0;
const THOUSAND = 1e3;
export const zoomAndCenterExactArr = (map, pos, arr = [], padding = [100, 100]) => {
  if (!arr || !pos || !map) {
    return
  }
  arr.forEach(layer => { layer.setStyle({ opacity: 0, fillOpacity: 0 }) });
  map.flyToBounds(pos, { duration: CONFIG.ZOOM_CENTER_ANIMATION_DURATION, padding });
  setTimeout(() => {
    arr.forEach(layer => { layer.setStyle({ opacity: 1, fillOpacity: CONFIG.FILL_OPACITY.NORMAL }) });
  }, CONFIG.ZOOM_CENTER_ANIMATION_DURATION * THOUSAND);
}

export const zoomAndCenter = (map, pos, layerGroup, padding) => {
  if (!layerGroup || !pos || !map) {
    return
  }
  const paddingArr = padding === UNDEFINED ? [100, 100] : [padding, padding];
  layerGroup.eachLayer(layer => layer.setStyle({ opacity: 0, fillOpacity: 0 }))
  map.flyToBounds(pos, { duration: CONFIG.ZOOM_CENTER_ANIMATION_DURATION, padding: paddingArr });
  setTimeout(() => {
    layerGroup?.eachLayer(layer => { layer.setStyle({ opacity: 1, fillOpacity: CONFIG.FILL_OPACITY.NORMAL }) })
  }, CONFIG.ZOOM_CENTER_ANIMATION_DURATION * THOUSAND);
}

export const createPolygon = (pos, config, id, handleClick, hasHover = true) => {
  const p = new L.Polygon(pos, config);
  p._id = id;
  p._icons = config?._icons;
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

export const createMap = (mapContainer, mapConfig, center = [CONFIG.CHINA_CENTER.lat, CONFIG.CHINA_CENTER.lng]) => {
  Provider(L);
  let map = L.map(mapContainer, {
    center: new L.LatLng(center[0], center[1]),
    zoom: 5,
    zoomControl: false,
    attributionControl: false,
    doubleClickZoom: false,
    ...mapConfig
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
    AreaLayer: {
      label: "地块轮廓",
      value: 1,
      imgSrc: 'https://oss.airoteach.cn/b8a6acc166a26469f445fa7e426c17628c9740eae6ffbbe0dd596dcc85895b82.png',
      activeImgSrc: 'https://oss.airoteach.cn/7b48cd94f38fafee49a99a8e97e6fc6cb44fec1a5bfe9a2a9e2d13cd2d78fc94.png',
    },
    IconLayer: {
      label: "地块图标",
      value: 2,
      imgSrc: 'https://oss.airoteach.cn/ece5375baf766a0b3de809ecf8d598ba25ac692fd9bf1eb035652da7fb84c304.png',
      activeImgSrc: 'https://oss.airoteach.cn/8d5473e633f1557edbdde510b26454acb0b5727ceb6f7b1c75070009bd77d588.png',
    },
    TextLayer: {
      label: "地块名称",
      value: 0,
      imgSrc: 'https://oss.airoteach.cn/f75608b9b595493f6427467b83ada57285d21595ce548f55d7016b5c5a7b1928.png',
      activeImgSrc: 'https://oss.airoteach.cn/92f97221f155671eec084d40e70880338b1007b3879b80e099305d791586f5b9.png',
    },
    FarmLayer: {
      label: "农场边界",
      value: 3,
      imgSrc: 'https://oss.airoteach.cn/3a398f11d955123e55519cc6ed56e532f30512e2de0baa52c18f79af0d819808.png',
      activeImgSrc: 'https://oss.airoteach.cn/ca02e046607067a0b157c1005b2990e8de0943577e0a1c2d5beeb338ac0bfa2c.png',
    },
    DeviceLayer: {
      label: "设备图标",
      value: 4,
      imgSrc: 'https://oss.airoteach.cn/927f3f033174bda76955a057e78510ce127d94464780863f2d480d341772adcf.png',
      activeImgSrc: 'https://oss.airoteach.cn/f2bcd820b550f774b62d35a864fce88ab0c6033b95da9ba3099f0c76d1e53541.png',
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

export const filterIcons = (item,shows) => {
  return item.cropsCreateReqVOList.some(({ crops }) => shows.includes(crops))
};

export const getStatuslist = () => [
  {
    label: CONFIG.LAYERS.AreaLayer.label,
    value: CONFIG.LAYERS.AreaLayer.value,
    imgSrc: CONFIG.LAYERS.AreaLayer.imgSrc,
    activeImgSrc: CONFIG.LAYERS.AreaLayer.activeImgSrc,
  },
  {
    label: CONFIG.LAYERS.IconLayer.label,
    value: CONFIG.LAYERS.IconLayer.value,
    imgSrc: CONFIG.LAYERS.IconLayer.imgSrc,
    activeImgSrc: CONFIG.LAYERS.IconLayer.activeImgSrc,
  },
  {
    label: CONFIG.LAYERS.TextLayer.label,
    value: CONFIG.LAYERS.TextLayer.value,
    imgSrc: CONFIG.LAYERS.TextLayer.imgSrc,
    activeImgSrc: CONFIG.LAYERS.TextLayer.activeImgSrc,
  },
  {
    label: CONFIG.LAYERS.FarmLayer.label,
    value: CONFIG.LAYERS.FarmLayer.value,
    imgSrc: CONFIG.LAYERS.FarmLayer.imgSrc,
    activeImgSrc: CONFIG.LAYERS.FarmLayer.activeImgSrc,
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

export const calcArea = (layer, limit) => {
  const [[first, ...arr]] = layer?._latlngs || [];
  let pointArr = [first, ...arr, first].map(({ lat, lng }) => [lat, lng]);
  const polygon = turf.polygon([pointArr]);
  const area = turf.area(polygon);

  return limit ? area: squareMeter2mu(area, 2);
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

export const OTHER_CROP_TYPE = {
  label: '其他',
  value: '-1'
};

export const OTHER_CROP = {
  img: '/images/crops/crop.png',
  label: OTHER_CROP_TYPE.label,
  val: OTHER_CROP_TYPE.value
};

export const formatShowStatuslist = (list = []) => (
  list.filter(({value}) => value !== CONFIG.LAYERS.IconLayer.value)
)

export const fomartShowRaiseCrops = (areaList = [], _crops = []) => {
  const hasCrops = new Set();
  const crops = _crops.concat({
    imageUrl: OTHER_CROP.img, 
    cropsName: OTHER_CROP.label, 
    code: OTHER_CROP.val
  });
  areaList.forEach(({ cropsCreateReqVOList }) => (
    cropsCreateReqVOList.forEach(({ crops }) => {
      hasCrops.add(crops)
    }))
  )
  const res = crops.filter(({ code }) => hasCrops.has(code))
  return res;
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

export const validateTel = (rule, value, callback) => {
  const isPhone = /^[1][3,4,5,7,8][0-9]{9}$/g/*手机号*/.test(value);

  const { length } = `${(value || '')}`.split('');
  if (value === UNDEFINED || value === null) {
    callback(new Error('请输入联系方式'))
  } else if (!isPhone) {
    callback(new Error('请输入正确手机号码'))
  } else if (length !== 11) {
    callback(new Error('请输入正确手机号码'))
  } else {
    callback();
  }
}

export const validateContacts = (rule, value, callback) => {
  const isZh = /^[\u4e00-\u9fa5|\.]+$/g/*中文. */.test(value);

  const { length } = `${(value || '')}`.split('');
  if (value === UNDEFINED || value === null) {
    callback(new Error('请输入联系人'))
  } else if (!isZh) {
    callback(new Error('只能输入 . 和中文'))
  } else if (length > 10) {
    callback(new Error('最多输入10位'))
  } else {
    callback();
  }
}

export const validateCropsOtherContent = (rule, value, callback) => {
  const isZh = /^[\u4e00-\u9fa5]+$/g/*中文. */.test(value);

  const { length } = `${(value || '')}`.split('');
  if (value === UNDEFINED || value === null || value === '') {
    callback(new Error('请输入种植作物名称'))
  } else if (!isZh) {
    callback(new Error('只能输入中文'))
  } else if (length > 10) {
    callback(new Error('最多输入10位'))
  } else {
    callback();
  }
}


export const validateCropsTypeOtherContent = (rule, value, callback) => {
  const isZh = /^[\u4e00-\u9fa5]+$/g/*中文. */.test(value);

  const { length } = `${(value || '')}`.split('');
  if (value === UNDEFINED || value === null || value === '') {
    callback(new Error('请输入作物品种名称'))
  } else if (!isZh) {
    callback(new Error('只能输入中文'))
  } else if (length > 10) {
    callback(new Error('最多输入10位'))
  } else {
    callback();
  }
}

export const validateCropsCreateReqVOList = (rule, value, callback) => {
  callback()
}

export const validateArea = (rule, value, callback) => {
  const [, decimal = 0] = `${+(value || 0)}`.split('.');
  const { length } = `${+(value || 0)}`.split('');
  if (value === UNDEFINED || value === null || value === 0 || value === '') {
    callback(new Error('请输入种植面积'))
  } else if (`${decimal}`.split('').length > 2 || value > 9999999.99) {
    callback(new Error('最多输入7位整数和2位小数'))
  } else if (value < 0) {
    callback(new Error('种植面积不能为负数'))
  } else {
    callback();
  }
}

export const validateLandName = (rule, value, callback) => {
  const isZh_Num = /^[\u4e00-\u9fa5|\d]+$/g/*中文数字 */.test(value);
  const { length } = `${value}`.split('');
  if (length === 0 || value === UNDEFINED || value === null) {
    callback(new Error('请输入地块名'));
  } else if (!isZh_Num) {
    callback(new Error('请输入中文或数字'));
  } else if (length > 20) {
    callback(new Error('最多输入20个字符'));
  } else {
    callback()
  }
};

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

// export const CACHE_KEY_LIST = [
// 'color',
// 'contacts',
// 'crops',
// 'cropsIsOther',
// 'cropsOtherContent',
// 'cropsType',
// 'cropsTypeIsOther',
// 'cropsTypeOtherContent',
// 'landName',
// 'tel'
// ]

// export const FORM_CACHE_KEY = 'FORM_CACHE_KEY';

// export const setFormCache = (keyList, obj) => {
//   const res = keyList.reduce((total,cur) => ({
//     [cur]: obj[cur],
//     ...total
//   }), {})
//   setStore(FORM_CACHE_KEY, res);
// }

// export const getFormCache = () => {
//   return getStore(FORM_CACHE_KEY);
// }
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