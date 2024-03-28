package com.airohit.agriculture.module.device.convert.info;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.device.dal.dataobject.info.DeviceInfoDO;
import com.airohit.agriculture.module.device.vo.info.DeviceInfoCreateReqVO;
import com.airohit.agriculture.module.device.vo.info.DeviceInfoRespVO;
import com.airohit.agriculture.module.device.vo.info.DeviceInfoUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 设备基本信息 Convert
 *
 * @author shiminghao
 */
@Mapper
public interface DeviceInfoConvert {

    DeviceInfoConvert INSTANCE = Mappers.getMapper(DeviceInfoConvert.class);

    DeviceInfoDO convert(DeviceInfoCreateReqVO bean);

    DeviceInfoDO convert(DeviceInfoUpdateReqVO bean);

    DeviceInfoRespVO convert(DeviceInfoDO bean);

    List<DeviceInfoRespVO> convertList(List<DeviceInfoDO> list);

    PageResult<DeviceInfoRespVO> convertPage(PageResult<DeviceInfoDO> page);

}
