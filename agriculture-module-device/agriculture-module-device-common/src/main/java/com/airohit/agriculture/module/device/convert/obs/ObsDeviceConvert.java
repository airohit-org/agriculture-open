package com.airohit.agriculture.module.device.convert.obs;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.device.dal.dataobject.obs.ObsDeviceDO;
import com.airohit.agriculture.module.device.vo.obs.ObsDeviceVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 设备基本信息 Convert
 *
 * @author shiminghao
 */
@Mapper
public interface ObsDeviceConvert {

    ObsDeviceConvert INSTANCE = Mappers.getMapper(ObsDeviceConvert.class);

    PageResult<ObsDeviceVo> convertPage(PageResult<ObsDeviceDO> page);

    ObsDeviceVo convert(ObsDeviceDO bean);

    List<ObsDeviceVo> convertList(List<ObsDeviceDO> obsDeviceDOList);
}
