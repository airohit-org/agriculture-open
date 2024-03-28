package com.airohit.agriculture.module.plant.dal.mysql.plan;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.plant.api.plan.dto.PlanRespDTO;
import com.airohit.agriculture.module.plant.dal.dataobject.plan.PlanDO;
import com.airohit.agriculture.module.plant.vo.plan.PlanBindLandVO;
import com.airohit.agriculture.module.plant.vo.plan.PlanExportReqVO;
import com.airohit.agriculture.module.plant.vo.plan.PlanPageReqVO;
import com.airohit.agriculture.module.plant.vo.plan.PlanRespVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 种植计划 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface PlanMapper extends BaseMapperX<PlanDO> {

    default PageResult<PlanDO> selectPage(PlanPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PlanDO>()
                .likeIfPresent(PlanDO::getPlanName, reqVO.getPlanName())
                .eqIfPresent(PlanDO::getCrops, reqVO.getCrops())
                .eqIfPresent(PlanDO::getCropsType, reqVO.getCropsType())
                .betweenIfPresent(PlanDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PlanDO::getId));
    }

    default List<PlanDO> selectList(PlanExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PlanDO>()
                .likeIfPresent(PlanDO::getPlanName, reqVO.getPlanName())
                .eqIfPresent(PlanDO::getCrops, reqVO.getCrops())
                .eqIfPresent(PlanDO::getCropsType, reqVO.getCropsType())
                .betweenIfPresent(PlanDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PlanDO::getId));
    }

    /**
     * 分页查询种植计划信息
     *
     * @param planPageReqVOPage
     * @param pageVO
     * @return
     */
    IPage<PlanRespVO> getPlanListPage(Page<PlanPageReqVO> planPageReqVOPage, @Param("plan") PlanPageReqVO pageVO);

    /**
     * 查询计划绑定地块信息
     *
     * @return
     */
    @Select("SELECT pp.id AS plantingPlanId,pp.plan_name AS planName,l.land_name AS landName,l.id AS landId FROM planting_plan pp LEFT JOIN land l ON l.id=pp.land_id WHERE pp.deleted=0 and l.deleted=0 AND l.id IS NOT NULL order by l.id desc")
    List<PlanBindLandVO> queryPlanBindLand();

    /**
     * 查询地块未绑定计划信息
     *
     * @return
     */
    List<PlanBindLandVO> queryLandPlan();

    /**
     * 根据地块编码查询计划信息
     *
     * @param landId
     * @return
     */
    @Select("select id as planId, plan_name as planName from planting_plan where land_id = #{landId} LIMIT 1")
    PlanRespDTO getPlanByLandId(@Param("landId") Integer landId);

    @Update("update planting_plan set land_id = NULL where id = #{planId}")
    void LandUnbindingByPlanId(@Param("planId") Integer planId);

    @Update("update planting_plan set land_id = NULL where land_id = #{landId}")
    void LandUnbindingByLandId(Integer landId);

    PlanRespVO getPlanById(@Param("id") Integer id);

}
