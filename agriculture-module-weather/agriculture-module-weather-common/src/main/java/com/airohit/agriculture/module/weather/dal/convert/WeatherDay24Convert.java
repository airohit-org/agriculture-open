package com.airohit.agriculture.module.weather.dal.convert;

import com.airohit.agriculture.module.weather.dal.dataobject.weather.WeatherHour;
import com.airohit.agriculture.module.weather.vo.day24.WeatherHourVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface WeatherDay24Convert {

    WeatherDay24Convert INSTANCE = Mappers.getMapper(WeatherDay24Convert.class);

    List<WeatherHourVO> convertList(List<WeatherHour> list);

}
