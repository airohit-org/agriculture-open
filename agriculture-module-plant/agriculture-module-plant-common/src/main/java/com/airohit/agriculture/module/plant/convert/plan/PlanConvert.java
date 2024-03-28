package com.airohit.agriculture.module.plant.convert.plan;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.dal.dataobject.plan.PlanDO;
import com.airohit.agriculture.module.plant.vo.plan.PlanCreateReqVO;
import com.airohit.agriculture.module.plant.vo.plan.PlanExcelVO;
import com.airohit.agriculture.module.plant.vo.plan.PlanRespVO;
import com.airohit.agriculture.module.plant.vo.plan.PlanUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 种植计划 Convert
 *
 * @author 管理员
 */
@Mapper
public interface PlanConvert {

    PlanConvert INSTANCE = Mappers.getMapper(PlanConvert.class);

    PlanDO convert(PlanCreateReqVO bean);

    PlanDO convert(PlanUpdateReqVO bean);

    PlanRespVO convert(PlanDO bean);

    List<PlanRespVO> convertList(List<PlanDO> list);

    PageResult<PlanRespVO> convertPage(PageResult<PlanDO> page);

    List<PlanExcelVO> convertList02(List<PlanDO> list);

}
