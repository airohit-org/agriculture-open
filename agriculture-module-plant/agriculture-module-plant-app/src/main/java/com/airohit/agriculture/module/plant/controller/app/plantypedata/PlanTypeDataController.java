package com.airohit.agriculture.module.plant.controller.app.plantypedata;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.plant.convert.plantypedata.PlanTypeDataConvert;
import com.airohit.agriculture.module.plant.dal.dataobject.plantypedata.PlanTypeDataDO;
import com.airohit.agriculture.module.plant.dal.dataobject.taskInfo.TaskInfoDO;
import com.airohit.agriculture.module.plant.service.plantypedata.PlanTypeDataService;
import com.airohit.agriculture.module.plant.service.taskInfo.TaskInfoService;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Api(tags = "APP - 种植计划阶段")
@RestController
@RequestMapping("/plant/plan-type-data")
@Validated
@Slf4j
public class PlanTypeDataController {

    @Resource
    private PlanTypeDataService planTypeDataService;

    @Resource
    private TaskInfoService taskInfoService;

    @GetMapping("/getPlanTypeDataByPlantingPlanId")
    @ApiOperation("根据种植计划编号查询种植计划阶段")
    public CommonResult<List<PlanTypeDataRespVO>> getPlanTypeDataByPlantingPlanId(@RequestParam("plantingPlanId") Integer plantingPlanId) {
        List<PlanTypeDataDO> planTypeDataDOParentList = planTypeDataService.getPlanTypeDataByPlantingPlanIdAndParent(plantingPlanId, 0);

        //List<PlanTypeDataDO> planTypeDataDOList = planTypeDataService.getPlanTypeDataByPlantingPlanId(plantingPlanId);

        List<PlanTypeDataRespVO> planTypeDataRespVOS = PlanTypeDataConvert.INSTANCE.convertList(planTypeDataDOParentList);

        for (PlanTypeDataRespVO planTypeDataRespVO : planTypeDataRespVOS) {

            List<PlanTypeDataDO> planTypeDataDOChildList = planTypeDataService.getPlanTypeDataByPlantingPlanIdAndParent(plantingPlanId, planTypeDataRespVO.getId());

            Map<String, List<TaskInfoDO>> taskInfoDOMap = taskInfoService.getTaskInfoDOMap(planTypeDataRespVO.getPlantingPlanId());

            List<PlanTypeDataRespVO> planTypeDataRespChildVOS = PlanTypeDataConvert.INSTANCE.convertList(planTypeDataDOChildList);

            planTypeDataRespVO.setPlanTypeDataChildList(planTypeDataRespChildVOS);

            for (PlanTypeDataRespVO planTypeDataChildRespVO : planTypeDataRespChildVOS) {
                planTypeDataChildRespVO.setTaskInfoList(taskInfoDOMap.get(planTypeDataChildRespVO.getStageCode()));
            }
        }

        return success(planTypeDataRespVOS);
    }


}
