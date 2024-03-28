import {WEATHER_CONFIG} from '@/utils/weatherConfig.js'

const {
    WEATHER_PHENOMENON,
    TEMPRATURE,
    APPARENT_TEMPRATURE,
    RELATIVE_HUMIDITY,
    WIND_LEVEL,
    WIND_SPEED,
    WIND_DIRECTION,
    WIND_ANGLE,
    RAINFALL_1_HOUR,
    CLOUDINESS,
    HORIZONTAL_VISIBILITY,
    PRESSURE,
    LEAKAGE_TEMPRATURE,
    UV_INDEX
} = WEATHER_CONFIG;

export const WEATHER_INDEX = {
    WEATHER_PHENOMENON,
    TEMPRATURE,
    APPARENT_TEMPRATURE,
    RELATIVE_HUMIDITY,
    WIND_LEVEL,
    WIND_SPEED,
    WIND_DIRECTION,
    WIND_ANGLE,
    RAINFALL_1_HOUR,
    CLOUDINESS,
    HORIZONTAL_VISIBILITY,
    PRESSURE,
    LEAKAGE_TEMPRATURE,
    UV_INDEX
}

export const matchKey = {
    WEATHER_PHENOMENON: 'text',
    TEMPRATURE: 'temp',
    APPARENT_TEMPRATURE: 'feelsLike',
    RELATIVE_HUMIDITY: 'rh',
    WIND_LEVEL: 'windClass',
    WIND_SPEED: 'windSpeed',
    WIND_DIRECTION: 'windDir',
    WIND_ANGLE: 'windAngle',
    RAINFALL_1_HOUR: 'prec',
    CLOUDINESS: 'clouds',
    HORIZONTAL_VISIBILITY: 'vis',
    PRESSURE: 'pressure',
    LEAKAGE_TEMPRATURE: 'dew',
    UV_INDEX: 'uv',
}

export const calcWeatherList = (values) => (
    Object.keys(WEATHER_INDEX).map((key) => 
        ({
            prompt: values?.[matchKey[key]],
            ...WEATHER_INDEX?.[key]
        })
    ).filter(item => item.prompt)
);