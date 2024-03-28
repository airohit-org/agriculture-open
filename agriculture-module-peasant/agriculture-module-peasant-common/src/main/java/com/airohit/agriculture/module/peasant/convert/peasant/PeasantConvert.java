package com.airohit.agriculture.module.peasant.convert.peasant;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.peasant.dal.dataobject.peasant.PeasantDO;
import com.airohit.agriculture.module.peasant.vo.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 农户 Convert
 *
 * @author lrj
 */
@Mapper
public interface PeasantConvert {

    PeasantConvert INSTANCE = Mappers.getMapper(PeasantConvert.class);

    PeasantDO convert(PeasantCreateReqVO bean);

    PeasantDO convert(PeasantUpdateReqVO bean);

    PeasantRespVO convert(PeasantDO bean);

    List<PeasantRespVO> convertList(List<PeasantDO> list);

    PageResult<PeasantRespVO> convertPage(PageResult<PeasantDO> page);

    List<PeasantExcelVO> convertList02(List<PeasantDO> list);

    PeasantDO convertExcel(Excel bean);

}
