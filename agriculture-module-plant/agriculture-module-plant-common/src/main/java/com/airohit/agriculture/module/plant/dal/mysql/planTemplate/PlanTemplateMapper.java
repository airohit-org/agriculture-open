package com.airohit.agriculture.module.plant.dal.mysql.planTemplate;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.plant.dal.dataobject.planTemplate.PlanTemplateDO;
import com.airohit.agriculture.module.plant.vo.planTemplate.PlanTemplateExportReqVO;
import com.airohit.agriculture.module.plant.vo.planTemplate.PlanTemplatePageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 计划模版 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PlanTemplateMapper extends BaseMapperX<PlanTemplateDO> {

    default PageResult<PlanTemplateDO> selectPage(PlanTemplatePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PlanTemplateDO>()
                .likeIfPresent(PlanTemplateDO::getPlanName, reqVO.getPlanName())
                .eqIfPresent(PlanTemplateDO::getCrops, reqVO.getCrops())
                .betweenIfPresent(PlanTemplateDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PlanTemplateDO::getId));
    }

    default List<PlanTemplateDO> selectList(PlanTemplateExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PlanTemplateDO>()
                .likeIfPresent(PlanTemplateDO::getPlanName, reqVO.getPlanName())
                .eqIfPresent(PlanTemplateDO::getCrops, reqVO.getCrops())
                .betweenIfPresent(PlanTemplateDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PlanTemplateDO::getId));
    }

}
