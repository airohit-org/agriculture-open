import { arr2json, dayjsFormatTime } from '@/utils';
import { str2Coordinate } from '@/utils/latlng';

export const initSearchOptions = (arealist = []) => (
  arealist.map(({
    id: value,
    name: label
  }) => ({ label, value }))
)


export const STATUS_ENUM = {
  OPEN: 0,
  CLOSED: 1
}

export const STATUS_MAP = {
  [STATUS_ENUM.OPEN]: '正常',
  [STATUS_ENUM.CLOSED]: '关闭',
}

export const MAX_ECLIPSE_NUMBER = 10;

export const calcEclipse = (arr, maxNumber) => {
  if (arr.join(',').length <= maxNumber) {
    return arr.join(',');
  }
  let res = '';
  let resArr = [];
  let i = 0;
  while(res.length<maxNumber){
    res+= `${arr[i]},`;
    resArr.push(arr[i]);
    i++;
  }
  return `${resArr.join(',')}...`;
};

export const formatlandPage = (dataArr) => (
  dataArr.map(item => {
    const { area, cropsCreateReqVOList = []} = item || {};
    const cropsNames = cropsCreateReqVOList.map(({cropsName}) => cropsName);
    const cropsTypeNames = cropsCreateReqVOList?.map(({cropsTypeName}) => cropsTypeName);
    return ({
      areaText: `${area}亩`,
      cropsTypeNames: cropsTypeNames.join(','),
      cropsNames: cropsNames.join(','),
      wholecropsTypeNames: calcEclipse(cropsTypeNames, MAX_ECLIPSE_NUMBER),
      wholecropsNames: calcEclipse(cropsNames, MAX_ECLIPSE_NUMBER),
      ...item,
    })
  })
)

export const formatLands = (dataArr) => (
  dataArr.map(item => {
    const {
      landCoordinateCenter,
      landName: name,
      posVoList,
      createTime,
      status,
      area,
      cropsCreateReqVOList = []
    } = item || {};
    const cropsNames = cropsCreateReqVOList.map(({cropsName}) => cropsName);
    const cropsTypeNames = cropsCreateReqVOList.map(({cropsTypeName}) => cropsTypeName);
    return {
      name,
      areaText: `${area}亩`,
      center: str2Coordinate(landCoordinateCenter),
      pos: posVoList?.map(({ latitude, longitude }) => ([latitude, longitude])),
      createTimeText: dayjsFormatTime(createTime),
      statusText: STATUS_MAP[status],
      cropsNames: cropsNames.join(','),
      cropsTypeNames: cropsTypeNames.join(','),
      wholecropsTypeNames: calcEclipse(cropsTypeNames, MAX_ECLIPSE_NUMBER),
      wholecropsNames: calcEclipse(cropsNames, MAX_ECLIPSE_NUMBER),
      ...item
    }
  }).filter(({ pos }) => !!pos)
)

export const PAGE_SIZE = 17;

export const formatRaiseCropsMap = (arr) => {
  const _arr = arr.map(item => ({
    ...item,
    cropsVarietiesMap: arr2json(item.cropsVarietiesList, 'code')
  }))
  return arr2json(_arr, 'code');
}