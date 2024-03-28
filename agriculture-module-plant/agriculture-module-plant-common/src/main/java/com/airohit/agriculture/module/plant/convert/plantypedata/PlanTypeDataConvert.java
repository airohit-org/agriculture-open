package com.airohit.agriculture.module.plant.convert.plantypedata;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.dal.dataobject.plantypedata.PlanTypeDataDO;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataCreateReqVO;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataExcelVO;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataRespVO;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 种植计划类型 Convert
 *
 * @author 管理员
 */
@Mapper
public interface PlanTypeDataConvert {

    PlanTypeDataConvert INSTANCE = Mappers.getMapper(PlanTypeDataConvert.class);

    PlanTypeDataDO convert(PlanTypeDataCreateReqVO bean);

    PlanTypeDataDO convert(PlanTypeDataUpdateReqVO bean);

    PlanTypeDataRespVO convert(PlanTypeDataDO bean);

    List<PlanTypeDataRespVO> convertList(List<PlanTypeDataDO> list);

    PageResult<PlanTypeDataRespVO> convertPage(PageResult<PlanTypeDataDO> page);

    List<PlanTypeDataExcelVO> convertList02(List<PlanTypeDataDO> list);

}
