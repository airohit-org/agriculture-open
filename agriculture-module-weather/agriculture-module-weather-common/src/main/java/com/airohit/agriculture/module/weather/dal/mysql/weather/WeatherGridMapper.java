package com.airohit.agriculture.module.weather.dal.mysql.weather;

import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.module.weather.dal.dataobject.weather.WeatherGrid;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WeatherGridMapper extends BaseMapperX<WeatherGrid> {
}
