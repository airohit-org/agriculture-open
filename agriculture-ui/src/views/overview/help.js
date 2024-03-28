import { THOUSAND } from '@/constants'

export const createPolygon = (pos, config) => {
  const p = new L.Polygon(pos, config);
  return p;
};

const CONFIG = {
  FILL_OPACITY: {
    NORMAL: .2,
    HIGHLIGHT: .7
  },
  ZOOM_CENTER_ANIMATION_DURATION: .25
}


const WARN_ONE_INFO_CONFIG = [
  {title: '气象预警', icon: 'https://oss.airoteach.cn/17b14cc6163627682e47da87cf0f4eb2510166649ef46adbd5ec7d5f197aa036.png'},
  {title: '设备预警', icon: 'https://oss.airoteach.cn/6f5ede98a31496703387cc2c56ef52c436b4627d691a68243cb6f59939d96ac9.png'},
  {title: '虫害预警', icon: 'https://oss.airoteach.cn/87905189aebc0d76e77429b25762947a77548fab9d405c42502586bca76b015e.png'},
  {title: '地块预警', icon: 'https://oss.airoteach.cn/6ce09a8a5703d8a9f470b84c4701e1ce316d5828f3bf9fbf54cf9466f8b9b34a.png'},
]

export const formatWarnOneInfoList = (list = []) => (
  list.map(({ warningType, warningMessage: text}) => ({
    ...WARN_ONE_INFO_CONFIG[warningType],
    text
  }))
)

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