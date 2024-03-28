package com.airohit.agriculture.module.plant.dal.mysql.prevention;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.plant.dal.dataobject.prevention.PreventionDO;
import com.airohit.agriculture.module.plant.vo.prevention.PreventionExportReqVO;
import com.airohit.agriculture.module.plant.vo.prevention.PreventionPageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 防治方案 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PreventionMapper extends BaseMapperX<PreventionDO> {

    default PageResult<PreventionDO> selectPage(PreventionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PreventionDO>()
                .likeIfPresent(PreventionDO::getDiseasesName, reqVO.getDiseasesName())
                .eqIfPresent(PreventionDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PreventionDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(PreventionDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PreventionDO::getPeasantId, reqVO.getPeasantId())
                .eqIfPresent(PreventionDO::getFarmTenantId, reqVO.getFarmTenantId())
                .eqIfPresent(PreventionDO::getMeasure, reqVO.getMeasure())
                .eqIfPresent(PreventionDO::getPreventionPlan, reqVO.getPreventionPlan())
                .orderByDesc(PreventionDO::getId));
    }

    default List<PreventionDO> selectList(PreventionExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PreventionDO>()
                .likeIfPresent(PreventionDO::getDiseasesName, reqVO.getDiseasesName())
                .eqIfPresent(PreventionDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PreventionDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(PreventionDO::getRemark, reqVO.getRemark())
                .eqIfPresent(PreventionDO::getPeasantId, reqVO.getPeasantId())
                .eqIfPresent(PreventionDO::getFarmTenantId, reqVO.getFarmTenantId())
                .eqIfPresent(PreventionDO::getMeasure, reqVO.getMeasure())
                .eqIfPresent(PreventionDO::getPreventionPlan, reqVO.getPreventionPlan())
                .orderByDesc(PreventionDO::getId));
    }

}
