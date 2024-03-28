package com.airohit.agriculture.module.weather.service;

import com.airohit.agriculture.module.plant.vo.plan.PlanCreateReqVO;
import com.airohit.agriculture.module.weather.dal.dataobject.weather.WeatherHour;
import com.airohit.agriculture.module.weather.dal.dataobject.weather.WeatherRadar;
import com.airohit.agriculture.module.weather.vo.day24.WeatherHourBaseVO;
import com.airohit.agriculture.module.weather.vo.day24.WeatherHourVO;
import com.airohit.agriculture.module.weather.vo.grid.WeatherGridVO;
import com.airohit.agriculture.module.weather.vo.damage.WeatherDamageVO;
import com.airohit.agriculture.module.weather.vo.radar.WeatherRadarVO;
import com.airohit.agriculture.module.weather.vo.rain.WeatherRainVO;
import com.airohit.agriculture.module.weather.vo.futureday.WeatherDaysVO;
import com.airohit.agriculture.module.weather.vo.today.WeatherVO;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

public interface WeatherService {

    WeatherVO getTodayWeather();

    List<WeatherDaysVO> getWeather10();

    List<WeatherRainVO> getWeatherRain();

    List<WeatherDamageVO> getWeatherDamage();

    WeatherGridVO getWeatherGrid(String lonlat);

    List<WeatherHour> getWeatherDay24();

    List<WeatherRadar> getWeatherRadar();
}
