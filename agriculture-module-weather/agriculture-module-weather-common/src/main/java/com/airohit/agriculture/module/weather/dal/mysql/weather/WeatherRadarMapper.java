package com.airohit.agriculture.module.weather.dal.mysql.weather;

import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.weather.dal.dataobject.weather.WeatherRadar;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Mapper
public interface WeatherRadarMapper extends BaseMapperX<WeatherRadar> {

    default List<WeatherRadar> determine(WeatherRadar weatherRadar){

        return selectList(new LambdaQueryWrapperX<WeatherRadar>()
                .likeIfPresent(WeatherRadar::getDate, String.valueOf(weatherRadar.getDate()))
                .likeIfPresent(WeatherRadar::getDateTime, String.valueOf(weatherRadar.getDateTime()))
        );
    }

    default List<WeatherRadar> selectRadar(Date first, Date last){

        return selectList(new LambdaQueryWrapperX<WeatherRadar>()
                .betweenIfPresent(WeatherRadar::getDateTime, first, last)
        );
    }



}
