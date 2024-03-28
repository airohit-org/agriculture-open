import customParseFormat from "dayjs/plugin/customParseFormat";
import dayjs from "dayjs";

dayjs.extend(customParseFormat);

export const MOCK = {
  farmDevice: [
    [47.850031, 133.877335],
    [47.814538, 134.066849],
    [47.722235, 133.853302]
  ],
  weatherInfoValues: {
    // WEATHER_PHENOMENON: '现象a'',
    TEMPRATURE: "25℃",
    APPARENT_TEMPRATURE: "25℃",
    RELATIVE_HUMIDITY: "25%rh",
    WIND_LEVEL: "2级",
    WIND_SPEED: "2m/s",
    WIND_DIRECTION: "北风",
    WIND_ANGLE: "45°",
    RAINFALL_1_HOUR: "250mm",
    CLOUDINESS: "多云",
    HORIZONTAL_VISIBILITY: "2Km",
    PRESSURE: "200hpa",
    LEAKAGE_TEMPRATURE: "25℃",
    UV_INDEX: "15",
  },
};

export const LAYERS = {
  RADAR: {
    id: 1,
    title: "雷达",
    icon: "https://oss.airoteach.cn/d9fe1eaa52e7043b288a20eab924c359e2ac94a8881c22645bdecc96951d9dec.png",
    activeIcon:
      "https://oss.airoteach.cn/675d554c71b176244df3ed65892c7bfe69ec86b882fcab6f745a7890e5146f60.png",
  },
  NET: {
    id: 2,
    title: "1x1公里网格",
    icon: "https://oss.airoteach.cn/83d7a501d437b124d65a8e5e6b17cdd6178f29a2ada11013fff2399c2e46013b.png",
    activeIcon:
      "https://oss.airoteach.cn/faf7ca14356c911b32e9ac50c45a131d6373c75a5a13c56648ceceb8fe1a55fb.png",
  },
  DEVICE: {
    id: 3,
    title: "气象站",
    icon: "https://oss.airoteach.cn/e6fbbf1374bb10fc338c0d6e3c0878f9b86a6a7059d142513eaa0698d83c18b3.png",
    activeIcon:
      "https://oss.airoteach.cn/0329acd04a2162f76f7741566079b1991cf52f73b9fcaf34d74c4bf29fb28197.png",
  },
};
// 暂时隐藏1*1公里网格功能
// export const LAYER_LIST = [LAYERS.RADAR, LAYERS.NET, LAYERS.DEVICE];
export const LAYER_LIST = [LAYERS.RADAR, LAYERS.DEVICE];

export const parseTime = (str) => dayjs(str, "YYYYMMDDHHmm").format("HH:mm");

export const formatRadarInfo = (list) =>
  list.map(({ time, ...rest }) => ({
    time: parseTime(time),
    ...rest,
  }));
