package com.airohit.agriculture.module.plant.convert.planTemplate;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.dal.dataobject.planTemplate.PlanTemplateDO;
import com.airohit.agriculture.module.plant.vo.planTemplate.PlanTemplateCreateReqVO;
import com.airohit.agriculture.module.plant.vo.planTemplate.PlanTemplateExcelVO;
import com.airohit.agriculture.module.plant.vo.planTemplate.PlanTemplateRespVO;
import com.airohit.agriculture.module.plant.vo.planTemplate.PlanTemplateUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 计划模版 Convert
 *
 * @author 管理员
 */
@Mapper
public interface PlanTemplateConvert {

    PlanTemplateConvert INSTANCE = Mappers.getMapper(PlanTemplateConvert.class);

    PlanTemplateDO convert(PlanTemplateCreateReqVO bean);

    PlanTemplateDO convert(PlanTemplateUpdateReqVO bean);

    PlanTemplateRespVO convert(PlanTemplateDO bean);

    List<PlanTemplateRespVO> convertList(List<PlanTemplateDO> list);

    PageResult<PlanTemplateRespVO> convertPage(PageResult<PlanTemplateDO> page);

    List<PlanTemplateExcelVO> convertList02(List<PlanTemplateDO> list);

}
