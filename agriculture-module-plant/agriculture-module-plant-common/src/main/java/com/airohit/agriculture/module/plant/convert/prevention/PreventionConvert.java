package com.airohit.agriculture.module.plant.convert.prevention;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.dal.dataobject.prevention.PreventionDO;
import com.airohit.agriculture.module.plant.vo.prevention.PreventionCreateReqVO;
import com.airohit.agriculture.module.plant.vo.prevention.PreventionExcelVO;
import com.airohit.agriculture.module.plant.vo.prevention.PreventionRespVO;
import com.airohit.agriculture.module.plant.vo.prevention.PreventionUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 防治方案 Convert
 *
 * @author 管理员
 */
@Mapper
public interface PreventionConvert {

    PreventionConvert INSTANCE = Mappers.getMapper(PreventionConvert.class);

    PreventionDO convert(PreventionCreateReqVO bean);

    PreventionDO convert(PreventionUpdateReqVO bean);

    PreventionRespVO convert(PreventionDO bean);

    List<PreventionRespVO> convertList(List<PreventionDO> list);

    PageResult<PreventionRespVO> convertPage(PageResult<PreventionDO> page);

    List<PreventionExcelVO> convertList02(List<PreventionDO> list);

}
