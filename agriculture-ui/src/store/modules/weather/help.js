import { arr2json, dayjsFormatTime } from "@/utils";
import { WEATHER_ICONS } from "../overview/help";
import { preloadImg } from "@/utils/index";
import customParseFormat from "dayjs/plugin/customParseFormat";
import dayjs from "dayjs";

dayjs.extend(customParseFormat);

export const formatRainFallInfo = (list = []) => list.map(({ rain }) => rain);
export const formatTimeDateInfo = (list = []) => list.map(({ dataTime }) => dayjsFormatTime(new Date(dataTime), "HH:mm"));
export const formatMsgList = (list = []) => list.map(({ msg }) => msg);

export const formatWeather24 = (list = []) =>
  list.map(
    ({
      code,
      dataTime,
      rh: humidity,
      tempFc: temperature,
      text: weather,
      windClass: windScale,
      windDir: windDirection,
      windSpeed,
    }) => ({
      iconWeather:
        WEATHER_ICONS[code] ||
        "https://oss.airoteach.cn/5e765b8a80dd6a69554af071d26528a19ea9070c6c16e19898f86e1ad07bf778.png",
      weather,
      temperature,
      temperatureUnit: "℃",
      humidity,
      humidityUnit: "%",
      windDirection,
      windSpeed,
      windSpeedUnit: "m/s",
      windScale,
      time: dayjsFormatTime(new Date(dataTime), "HH:mm"),
    })
  );

export const parseTime = (str) => dayjs(str, "HHmmss").format("HH:mm");

export const formatRadar = (list = []) => {
  const res = list.slice(-25).map(({ time, img: src }) => ({
    src,
    time: parseTime(time),
  }));
  preloadImg(res.map(({ src }) => src));
  return res;
};

export const formatDamage = (list = []) => list.map(({ typeId }) => typeId);

export const formatGrid = (list) => {
  return arr2json(list, "landId");
};

export const formatFarmDevice = (list = []) => (
  list.map(({ coordinate, ...rest }) => ({
    ...rest,
    coordinate: coordinate.split(","),
  }))
)
  
