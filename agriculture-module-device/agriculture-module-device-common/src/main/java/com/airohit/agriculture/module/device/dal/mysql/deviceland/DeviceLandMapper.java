package com.airohit.agriculture.module.device.dal.mysql.deviceland;

import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.module.device.dal.dataobject.land.DeviceLandDO;
import com.airohit.agriculture.module.device.vo.land.DeviceLandListVo;
import com.airohit.agriculture.module.device.vo.obs.DeviceGroupVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 地块下的设备 Mapper
 *
 * @author shiminghao
 */
@Mapper
public interface DeviceLandMapper extends BaseMapperX<DeviceLandDO> {


    List<DeviceGroupVo> getDeviceGroupVoList(@Param("tenantId") Long tenantId);

    List<DeviceLandListVo> getDeviceLandListVoList(@Param("tenantId") Long tenantId, @Param("deviceId") Integer deviceId);

}
