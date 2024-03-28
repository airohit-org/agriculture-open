package com.airohit.agriculture.module.plant.api.plan;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.plant.api.plan.dto.PlanRespDTO;
import com.airohit.agriculture.module.plant.vo.plan.PlanCreateReqVO;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataCreateReqVO;


public interface PlanApi {
    CommonResult<Boolean> landBindPlan(Integer planId, Integer landId);

    CommonResult<PlanRespDTO> getPlan(Integer landId);

    CommonResult<Integer> createGroupPlan(PlanCreateReqVO createReqVO);

    CommonResult<Integer> createGroupPlanTypeData(PlanTypeDataCreateReqVO createReqVO);


}
