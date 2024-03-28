package com.airohit.agriculture.module.device.dal.mysql.obs;

import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.module.device.dal.dataobject.obs.ObsDeviceDO;
import com.airohit.agriculture.module.device.vo.obs.DeviceGroupVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 山东仁科设备基本信息 mapper
 * @Author: hanliyao
 * @Date: 2023/7/11 17:07
 */
@Mapper
public interface ObsDeviceMapper extends BaseMapperX<ObsDeviceDO> {

    List<DeviceGroupVo> getDeviceGroupVoList(@Param("farmId") Integer farmId);

    List<DeviceGroupVo> getAllDeviceGroupVoList(@Param("farmId") Integer farmId);
}
