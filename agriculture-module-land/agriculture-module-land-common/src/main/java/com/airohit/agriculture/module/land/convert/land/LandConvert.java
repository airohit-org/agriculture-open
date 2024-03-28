package com.airohit.agriculture.module.land.convert.land;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.land.dal.dataobject.land.LandDO;
import com.airohit.agriculture.module.land.vo.LandCreateReqVO;
import com.airohit.agriculture.module.land.vo.LandExcelVO;
import com.airohit.agriculture.module.land.vo.LandRespVO;
import com.airohit.agriculture.module.land.vo.LandUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 地块信息 Convert
 *
 * @author 管理员
 */
@Mapper
public interface LandConvert {

    LandConvert INSTANCE = Mappers.getMapper(LandConvert.class);

    LandDO convert(LandCreateReqVO bean);

    LandDO convert(LandUpdateReqVO bean);

    LandRespVO convert(LandDO bean);

    List<LandRespVO> convertList(List<LandDO> list);

    PageResult<LandRespVO> convertPage(PageResult<LandDO> page);

    List<LandExcelVO> convertList02(List<LandDO> list);

}
