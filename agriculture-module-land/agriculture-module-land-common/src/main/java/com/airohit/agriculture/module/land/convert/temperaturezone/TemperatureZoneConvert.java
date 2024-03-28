package com.airohit.agriculture.module.land.convert.temperaturezone;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.land.dal.dataobject.temperaturezone.TemperatureZoneDO;
import com.airohit.agriculture.module.land.vo.temperaturezone.TemperatureZoneCreateReqVO;
import com.airohit.agriculture.module.land.vo.temperaturezone.TemperatureZoneRespVO;
import com.airohit.agriculture.module.land.vo.temperaturezone.TemperatureZoneUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


/**
 * 积温带管理 Convert
 *
 * @author shiminghao
 */
@Mapper
public interface TemperatureZoneConvert {

    TemperatureZoneConvert INSTANCE = Mappers.getMapper(TemperatureZoneConvert.class);

    TemperatureZoneDO convert(TemperatureZoneCreateReqVO bean);

    TemperatureZoneDO convert(TemperatureZoneUpdateReqVO bean);

    TemperatureZoneRespVO convert(TemperatureZoneDO bean);

    List<TemperatureZoneRespVO> convertList(List<TemperatureZoneDO> list);

    PageResult<TemperatureZoneRespVO> convertPage(PageResult<TemperatureZoneDO> page);

}
