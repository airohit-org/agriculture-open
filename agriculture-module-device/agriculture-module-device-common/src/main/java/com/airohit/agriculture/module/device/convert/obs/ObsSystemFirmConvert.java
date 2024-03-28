package com.airohit.agriculture.module.device.convert.obs;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.device.dal.dataobject.obs.ObsSystemFirmDO;
import com.airohit.agriculture.module.device.vo.obs.ObsSystemFirmVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 设备详细信息 Convert
 *
 * @author hanliyao
 */
@Mapper
public interface ObsSystemFirmConvert {

    ObsSystemFirmConvert INSTANCE = Mappers.getMapper(ObsSystemFirmConvert.class);


    PageResult<ObsSystemFirmVo> convertPage(PageResult<ObsSystemFirmDO> page);

    ObsSystemFirmVo convert(ObsSystemFirmDO bean);

}
