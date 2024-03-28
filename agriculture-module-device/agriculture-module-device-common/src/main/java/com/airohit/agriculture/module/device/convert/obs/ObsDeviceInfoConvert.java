package com.airohit.agriculture.module.device.convert.obs;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.device.dal.dataobject.obs.ObsDeviceInfoDO;
import com.airohit.agriculture.module.device.vo.obs.ObsDeviceInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 设备详细信息 Convert
 *
 * @author hanliyao
 */
@Mapper
public interface ObsDeviceInfoConvert {

    ObsDeviceInfoConvert INSTANCE = Mappers.getMapper(ObsDeviceInfoConvert.class);


    PageResult<ObsDeviceInfoVo> convertPage(PageResult<ObsDeviceInfoDO> page);

    List<ObsDeviceInfoVo> convertList(List<ObsDeviceInfoDO> deviceInfoDOS);

}
