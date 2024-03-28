package com.airohit.agriculture.module.weather.dal.mysql.weather;

import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.weather.dal.dataobject.weather.WeatherHour;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WeatherHourMapper extends BaseMapperX<WeatherHour> {

    default List<WeatherHour> selectDay24(Long id,String date){
        return selectList(new LambdaQueryWrapperX<WeatherHour>()
                .likeIfPresent(WeatherHour::getDate, date)
                .likeIfPresent(WeatherHour::getFarmTenantId, String.valueOf(id))
        );
    }



}
