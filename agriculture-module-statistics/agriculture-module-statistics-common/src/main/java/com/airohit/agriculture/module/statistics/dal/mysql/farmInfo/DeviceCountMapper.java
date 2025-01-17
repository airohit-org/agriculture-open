package com.airohit.agriculture.module.statistics.dal.mysql.farmInfo;

import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.module.statistics.dal.dataobject.farmInfo.DeviceDO;
import com.airohit.agriculture.module.statistics.dal.dataobject.farmInfo.LandDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceCountMapper extends BaseMapperX<DeviceDO> {

    Long selectCountByFarmId(Integer farmId);


}
