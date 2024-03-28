package com.airohit.agriculture.module.plant.dal.mysql.plantypedata;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.plant.dal.dataobject.plantypedata.PlanTypeDataDO;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataExportReqVO;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataPageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 种植计划类型 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PlanTypeDataMapper extends BaseMapperX<PlanTypeDataDO> {

    default PageResult<PlanTypeDataDO> selectPage(PlanTypeDataPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PlanTypeDataDO>()
                .eqIfPresent(PlanTypeDataDO::getSort, reqVO.getSort())
                .likeIfPresent(PlanTypeDataDO::getStageName, reqVO.getStageName())
                .eqIfPresent(PlanTypeDataDO::getStageCode, reqVO.getStageCode())
                .eqIfPresent(PlanTypeDataDO::getPlantingPlanId, reqVO.getPlantingPlanId())
                .eqIfPresent(PlanTypeDataDO::getPeriod, reqVO.getPeriod())
                .betweenIfPresent(PlanTypeDataDO::getPlantingPlanDate, reqVO.getPlantingPlanDate())
                .eqIfPresent(PlanTypeDataDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(PlanTypeDataDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PlanTypeDataDO::getPlantingPlanDate));
    }

    default List<PlanTypeDataDO> selectList(PlanTypeDataExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PlanTypeDataDO>()
                        .eqIfPresent(PlanTypeDataDO::getSort, reqVO.getSort())
                        .likeIfPresent(PlanTypeDataDO::getStageName, reqVO.getStageName())
                        .eqIfPresent(PlanTypeDataDO::getStageCode, reqVO.getStageCode())
                        .eqIfPresent(PlanTypeDataDO::getPlantingPlanId, reqVO.getPlantingPlanId())
                        .eqIfPresent(PlanTypeDataDO::getPeriod, reqVO.getPeriod())
                        .betweenIfPresent(PlanTypeDataDO::getPlantingPlanDate, reqVO.getPlantingPlanDate())
                        .eqIfPresent(PlanTypeDataDO::getRemark, reqVO.getRemark())
                        .betweenIfPresent(PlanTypeDataDO::getCreateTime, reqVO.getCreateTime())
                        .orderByAsc(PlanTypeDataDO::getPlantingPlanDate)
                        .orderByAsc(PlanTypeDataDO::getCreateTime)
                //.orderByDesc(PlanTypeDataDO::getStageCode)
        );
    }

    default List<PlanTypeDataDO> selectParentList(PlanTypeDataExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PlanTypeDataDO>()
                        .eqIfPresent(PlanTypeDataDO::getSort, reqVO.getSort())
                        .likeIfPresent(PlanTypeDataDO::getStageName, reqVO.getStageName())
                        .eqIfPresent(PlanTypeDataDO::getStageCode, reqVO.getStageCode())
                        .eqIfPresent(PlanTypeDataDO::getParentId, reqVO.getParentId())
                        .eqIfPresent(PlanTypeDataDO::getPlantingPlanId, reqVO.getPlantingPlanId())
                        .eqIfPresent(PlanTypeDataDO::getPeriod, reqVO.getPeriod())
                        .betweenIfPresent(PlanTypeDataDO::getPlantingPlanDate, reqVO.getPlantingPlanDate())
                        .eqIfPresent(PlanTypeDataDO::getRemark, reqVO.getRemark())
                        .betweenIfPresent(PlanTypeDataDO::getCreateTime, reqVO.getCreateTime())
                        .orderByAsc(PlanTypeDataDO::getPlantingPlanDate)
                        .orderByAsc(PlanTypeDataDO::getCreateTime)
                //.orderByDesc(PlanTypeDataDO::getStageCode)
        );
    }
}
