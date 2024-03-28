import { isMeaningFullBatch, dayjsFormatTime } from '@/utils';
import {MATCH, CODES} from '@/utils/weatherCode';

export const AGRICULTURE_TASK_COLOR_LIST = [
  "#1FFACE",
  "#07D1FA",
  "#FCF366",
  "#6DDF80",
  "#FF9500",
  "#AF52DE",
];

// 天气的icon
export const WEATHER_ICONS = {
  '00': MATCH[CODES.Sunny],
  '01': MATCH[CODES.Cloudy],
  '01': MATCH[CODES.Cloudy],
  '02': MATCH[CODES.Yin],
  '03': MATCH[CODES.LittleShower],
  '04': MATCH[CODES.SporadicShowers],
  '05': MATCH[CODES.LocalShowers],
  '06': MATCH[CODES.RainSnow],
  '07': MATCH[CODES.Drizzle],
  '08': MATCH[CODES.Rain],
  '09': MATCH[CODES.Rain],
  '10': MATCH[CODES.Rain],
  '11': MATCH[CODES.Rain],
  '12': MATCH[CODES.Rain],
  '13': MATCH[CODES.BlowingSnow],
  '14': MATCH[CODES.Snowstorm],
  '15': MATCH[CODES.LittleSnow],
  '16': MATCH[CODES.Snow],
  '17': MATCH[CODES.Snow],
  '18': MATCH[CODES.Mist],
  '19': MATCH[CODES.FrozenRain],
  '20': MATCH[CODES.Sandstorm],
  '21': MATCH[CODES.Rain],
  '22': MATCH[CODES.Rain],
  '23': MATCH[CODES.Rain],
  '24': MATCH[CODES.Rain],
  '25': MATCH[CODES.Rain],
  '26': MATCH[CODES.LittleSnow],
  '27': MATCH[CODES.Snow],
  '28': MATCH[CODES.Snow],
  '29': MATCH[CODES.Little_Mist],
  '30': MATCH[CODES.Sandstorm],
  '31': MATCH[CODES.Sandstorm],
  '32': MATCH[CODES.Smoke],
  '33': MATCH[CODES.Wind],
  '34': MATCH[CODES.Wind],
  '35': MATCH[CODES.Little_Mist],
  '49': MATCH[CODES.Smoke],
  '53': MATCH[CODES.Haze],
  '54': MATCH[CODES.Haze],
  '55': MATCH[CODES.Haze],
  '56': MATCH[CODES.Haze],
  '57': MATCH[CODES.Smoke],
  '301':MATCH[CODES.Rain],
  '302':MATCH[CODES.Snow],
}

export const formatFarmInfoList = ({
  deviceCount,
  landCount,
  memberCount,
  crops,
}) => {
  if (!isMeaningFullBatch([deviceCount, landCount, memberCount, crops])) {
    return [];
  }

  console.log();

  return [
    {
      id: 1,
      title: "地块数量",
      image:
        "https://oss.airoteach.cn/e14b39e72b5830755286fe07ecfa710166deb4c47f66eecfd16c54f7d7206ace.png",
      unit: "块",
      color: "#1FFACE",
      value: landCount,
    },
    {
      id: 2,
      title: "设备数量",
      image:
        "https://oss.airoteach.cn/1b46db97b491c7e7c43be19e12cee77f43509752c86d9f6015ec8e7487d9edb3.png",
      value: deviceCount,
      unit: "个",
      color: "#07D1FA",
    },
    {
      id: 3,
      title: "作物数量",
      image:
        "https://oss.airoteach.cn/6c25a89022865ba19c0c1b13c1b98940bd39503fe4bd51d6d26f262c92782c78.png",
      value: crops,
      unit: "种",
      color: "#FFD15C",
    },
    {
      id: 4,
      title: "员工数量",
      image:
        "https://oss.airoteach.cn/d79ba7a84f2fc9e4d90b1672ffc7476f97529ef96516f8a1d16185057b45bbf8.png",
      value: memberCount,
      unit: "人",
      color: "#20E6A4",
    },
  ];
};

export const formatWarnList = (list = []) => (list.map(item => ({
  ...item,
  time: dayjsFormatTime(item.time, 'YYYY-MM-DD HH:mm:ss')
})))

export const formatTaskList = (list = []) => {
  const total = list.reduce((total, { value = 0 } = {}) => total + value, 0);
  return list.map(({ value, ...item }, index) => ({
    value,
    color: AGRICULTURE_TASK_COLOR_LIST[index],
    percentValue: ((value / total) * 100).toFixed(2),
    ...item,
  }));
};

export const formatTodayWeather = (todayWeather) => {
  const {
    carbonDioxideConcentration,
    humidity,
    maximumTemperature,
    minimumTemperature,
    rainfall,
    temperature,
    weatherCode,
    weatherName,
    windDirection,
    windSpeed,
  } = todayWeather || {};

  const weatherInfo = [
    {
      item: 1,
      text: "温度",
      value: temperature || '-',
      unit: "℃",
    },
    {
      item: 2,
      text: "风速",
      value: windSpeed || '-',
      unit: "km/h",
    },
    {
      item: 3,
      text: "湿度",
      value: humidity || '-',
      unit: "Rh",
    },
    {
      item: 4,
      text: "风向",
      value: windDirection || '-',
      unit: "",
    },
    {
      item: 5,
      text: "降雨量",
      value: rainfall || '-',
      unit: "mm",
    },
    {
      item: 6,
      text: "二氧化碳浓度",
      value: carbonDioxideConcentration || '-',
      unit: "co2",
    },
  ];

  return {
    weather: weatherName,
    todayMaxTemperature: maximumTemperature,
    todayMinTemperature: minimumTemperature,
    iconWeather: WEATHER_ICONS[weatherCode] || 'https://oss.airoteach.cn/5e765b8a80dd6a69554af071d26528a19ea9070c6c16e19898f86e1ad07bf778.png',
    weatherInfo
  }
};

export const formatWeather10 = (list) => (
  list.reduce((total, current) => {
    total.maximumTemperature.push(current.maximumTemperature);
    total.minimumTemperature.push(current.minimumTemperature);
    total.dataTime.push(current.weatherDate);
    return total;
  }, {
    maximumTemperature: [],
    minimumTemperature: [],
    dataTime: [],
  })
);
