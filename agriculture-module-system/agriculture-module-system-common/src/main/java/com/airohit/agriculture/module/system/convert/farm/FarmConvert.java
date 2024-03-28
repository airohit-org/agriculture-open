package com.airohit.agriculture.module.system.convert.farm;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.api.slave.vo.farm.FarmCreateReqVO;
import com.airohit.agriculture.module.system.api.slave.vo.farm.FarmRespVO;
import com.airohit.agriculture.module.system.api.slave.vo.farm.FarmUpdateReqVO;
import com.airohit.agriculture.module.system.dal.dataobject.farm.FarmDO;
import com.airohit.agriculture.module.system.entity.admin.farm.vo.FarmExcelVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


/**
 * 农场 Convert
 *
 * @author shiminghao
 */
@Mapper
public interface FarmConvert {

    FarmConvert INSTANCE = Mappers.getMapper(FarmConvert.class);

    FarmDO convert(FarmCreateReqVO bean);

    FarmDO convert(FarmUpdateReqVO bean);

    FarmRespVO convert(FarmDO bean);

    List<FarmRespVO> convertList(List<FarmDO> list);

    PageResult<FarmRespVO> convertPage(PageResult<FarmDO> page);

    List<FarmExcelVO> convertList02(List<FarmDO> list);

}
