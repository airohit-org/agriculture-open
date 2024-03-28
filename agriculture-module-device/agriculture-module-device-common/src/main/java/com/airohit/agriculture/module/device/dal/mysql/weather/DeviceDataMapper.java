package com.airohit.agriculture.module.device.dal.mysql.weather;

import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.module.device.dal.dataobject.weather.DeviceDataDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 天气设备数据 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface DeviceDataMapper extends BaseMapperX<DeviceDataDO> {


    /**
     * 获取时间范围内的降雨量总和
     *
     * @param startTime
     * @param endTime
     * @return
     */
    Double getRain(@Param("startTime") String startTime, @Param("endTime") String endTime);

}
