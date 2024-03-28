package com.airohit.agriculture.module.land.convert.raise;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.land.dal.dataobject.raise.RaiseCropsDO;
import com.airohit.agriculture.module.land.vo.raise.RaiseCropsCreateReqVO;
import com.airohit.agriculture.module.land.vo.raise.RaiseCropsRespVO;
import com.airohit.agriculture.module.land.vo.raise.RaiseCropsUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 种植作物 Convert
 *
 * @author shiminghao
 */
@Mapper
public interface RaiseCropsConvert {

    RaiseCropsConvert INSTANCE = Mappers.getMapper(RaiseCropsConvert.class);

    RaiseCropsDO convert(RaiseCropsCreateReqVO bean);

    RaiseCropsDO convert(RaiseCropsUpdateReqVO bean);

    RaiseCropsRespVO convert(RaiseCropsDO bean);

    List<RaiseCropsRespVO> convertList(List<RaiseCropsDO> list);

    PageResult<RaiseCropsRespVO> convertPage(PageResult<RaiseCropsDO> page);


}
