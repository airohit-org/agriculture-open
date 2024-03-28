package com.airohit.agriculture.module.weather.dal.mysql.weather;

import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.weather.dal.dataobject.weather.FarmDO;
import com.airohit.agriculture.module.weather.dal.dataobject.weather.WeatherHour;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WeatherFarmMapper extends BaseMapperX<FarmDO> {

    default List<FarmDO> selectLonlat(){
        return selectList( new LambdaQueryWrapperX<FarmDO>()
                .likeIfPresent(FarmDO:: getDeleted, String.valueOf(0))

        );
    }

}
