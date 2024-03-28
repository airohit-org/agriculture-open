package com.airohit.agriculture.module.plant.controller.admin.plantypedata;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.convert.plantypedata.PlanTypeDataConvert;
import com.airohit.agriculture.module.plant.dal.dataobject.plantypedata.PlanTypeDataDO;
import com.airohit.agriculture.module.plant.dal.dataobject.taskInfo.TaskInfoDO;
import com.airohit.agriculture.module.plant.service.plantypedata.PlanTypeDataService;
import com.airohit.agriculture.module.plant.service.taskInfo.TaskInfoService;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataCreateReqVO;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataPageReqVO;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataRespVO;
import com.airohit.agriculture.module.plant.vo.plantypedata.PlanTypeDataUpdateReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Api(tags = "管理后台 - 种植计划阶段")
@RestController
@RequestMapping("/plant/plan-type-data")
@Validated
@Slf4j
public class PlanTypeDataController {

    @Resource
    private PlanTypeDataService planTypeDataService;

    @Resource
    private TaskInfoService taskInfoService;

    @PostMapping("/create")
    @ApiOperation("创建种植计划阶段")
    @PreAuthorize("@ss.hasPermission('plant:plan-type-data:create')")
    public CommonResult<Integer> createPlanTypeData(@Valid @RequestBody PlanTypeDataCreateReqVO createReqVO) {
        return success(planTypeDataService.createPlanTypeData(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新种植计划阶段")
    @PreAuthorize("@ss.hasPermission('plant:plan-type-data:update')")
    public CommonResult<Boolean> updatePlanTypeData(@Valid @RequestBody PlanTypeDataUpdateReqVO updateReqVO) {
        planTypeDataService.updatePlanTypeData(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除种植计划阶段")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('plant:plan-type-data:delete')")
    public CommonResult<Boolean> deletePlanTypeData(@RequestParam("id") Integer id) {
        planTypeDataService.deletePlanTypeData(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得种植计划阶段")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('plant:plan-type-data:query')")
    public CommonResult<PlanTypeDataRespVO> getPlanTypeData(@RequestParam("id") Integer id) {
        PlanTypeDataDO planTypeData = planTypeDataService.getPlanTypeData(id);
        return success(PlanTypeDataConvert.INSTANCE.convert(planTypeData));
    }

    @GetMapping("/list")
    @ApiOperation("获得种植计划阶段列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('plant:plan-type-data:query')")
    public CommonResult<List<PlanTypeDataRespVO>> getPlanTypeDataList(@RequestParam("ids") Collection<Integer> ids) {
        List<PlanTypeDataDO> list = planTypeDataService.getPlanTypeDataList(ids);
        return success(PlanTypeDataConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得种植计划阶段分页")
    @PreAuthorize("@ss.hasPermission('plant:plan-type-data:query')")
    public CommonResult<PageResult<PlanTypeDataRespVO>> getPlanTypeDataPage(@Valid PlanTypeDataPageReqVO pageVO) {
        PageResult<PlanTypeDataDO> pageResult = planTypeDataService.getPlanTypeDataPage(pageVO);
        return success(PlanTypeDataConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/getPlanTypeDataByPlantingPlanId")
    @ApiOperation("根据种植计划编号查询种植计划阶段")
    @PreAuthorize("@ss.hasPermission('plant:plan-type-data:query')")
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

    @GetMapping("/getPlanTypeDataTaskByPlantingPlanId")
    @ApiOperation("根据种植计划编号查询种植计划阶段-任务")
    @PreAuthorize("@ss.hasPermission('plant:plan-type-data:query')")
    public CommonResult<List<PlanTypeDataRespVO>> getPlanTypeDataTaskByPlantingPlanId(@RequestParam("plantingPlanId") Integer plantingPlanId) {
        List<PlanTypeDataDO> planTypeDataDOList = planTypeDataService.getPlanTypeDataByPlantingPlanId(plantingPlanId);
        List<PlanTypeDataRespVO> planTypeDataRespVOS = PlanTypeDataConvert.INSTANCE.convertList(planTypeDataDOList);
        return success(planTypeDataRespVOS);
    }

}
