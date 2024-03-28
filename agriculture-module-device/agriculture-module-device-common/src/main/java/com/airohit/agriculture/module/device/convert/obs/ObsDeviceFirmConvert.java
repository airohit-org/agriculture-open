package com.airohit.agriculture.module.device.convert.obs;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.device.dal.dataobject.obs.ObsDeviceFirmDO;
import com.airohit.agriculture.module.device.vo.obs.ObsDeviceFirmVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 设备详细信息 Convert
 *
 * @author hanliyao
 */
@Mapper
public interface ObsDeviceFirmConvert {

    ObsDeviceFirmConvert INSTANCE = Mappers.getMapper(ObsDeviceFirmConvert.class);


    PageResult<ObsDeviceFirmVo> convertPage(PageResult<ObsDeviceFirmDO> page);

    ObsDeviceFirmVo convert(ObsDeviceFirmDO bean);

}
