package com.airohit.agriculture.module.weather.dal.convert;

import com.airohit.agriculture.module.weather.dal.dataobject.weather.WeatherRadar;
import com.airohit.agriculture.module.weather.vo.radar.WeatherRadarVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2024/3/28 13:54
 */
@Mapper
public interface WeatherRadarConvert {
    WeatherRadarConvert INSTANCE = Mappers.getMapper(WeatherRadarConvert.class);

    List<WeatherRadarVO> convertList(List<WeatherRadar> list);
}
