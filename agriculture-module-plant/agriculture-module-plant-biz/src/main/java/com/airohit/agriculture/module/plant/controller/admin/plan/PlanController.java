package com.airohit.agriculture.module.plant.controller.admin.plan;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.convert.plan.PlanConvert;
import com.airohit.agriculture.module.plant.dal.dataobject.plan.PlanDO;
import com.airohit.agriculture.module.plant.service.plan.PlanService;
import com.airohit.agriculture.module.plant.vo.plan.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Api(tags = "管理后台 - 种植计划")
@RestController
@RequestMapping("/plant/plan")
@Validated
public class PlanController {

    @Resource
    private PlanService planService;

    @PostMapping("/create")
    @ApiOperation("创建种植计划")
    @PreAuthorize("@ss.hasPermission('plant:plan:create')")
    public CommonResult<Integer> createPlan(@Valid @RequestBody PlanCreateReqVO createReqVO) {
        return success(planService.createPlan(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新种植计划")
    @PreAuthorize("@ss.hasPermission('plant:plan:update')")
    public CommonResult<Boolean> updatePlan(@Valid @RequestBody PlanUpdateReqVO updateReqVO) {
        planService.updatePlan(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除种植计划")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('plant:plan:delete')")
    public CommonResult<Boolean> deletePlan(@RequestParam("id") Integer id) {
        planService.deletePlan(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得种植计划")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('plant:plan:query')")
    public CommonResult<PlanRespVO> getPlan(@RequestParam("id") Integer id) {
        PlanRespVO planRespVO = planService.getPlanById(id);
        return success(planRespVO);
    }

    @GetMapping("/list")
    @ApiOperation("获得种植计划列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('plant:plan:query')")
    public CommonResult<List<PlanRespVO>> getPlanList(@RequestParam("ids") Collection<Integer> ids) {
        List<PlanDO> list = planService.getPlanList(ids);
        return success(PlanConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得种植计划分页")
    @PreAuthorize("@ss.hasPermission('plant:plan:query')")
    public CommonResult<PageResult<PlanRespVO>> getPlanPage(@Valid PlanPageReqVO pageVO) {
        PageResult<PlanRespVO> pageResult = planService.getPlanListPage(pageVO);
        return success(pageResult);
    }

    @GetMapping("/allList")
    @ApiOperation("获得所有种植计划")
    public CommonResult<List<PlanRespVO>> getAllList() {
        List<PlanDO> list = planService.getAllList();
        return success(PlanConvert.INSTANCE.convertList(list));
    }


    @PostMapping("/clone")
    @ApiOperation("克隆")
    @PreAuthorize("@ss.hasPermission('plant:plan:create')")
    public CommonResult<Boolean> clone(@Valid @RequestBody PlanCloneReqVO planCloneReqVO) {
        planService.clone(planCloneReqVO);
        return success(true);
    }

    @PostMapping("/cloneTemplate")
    @ApiOperation("克隆模版")
    @PreAuthorize("@ss.hasPermission('plant:plan:create')")
    public CommonResult<Boolean> cloneTemplate(@Valid @RequestBody PlanCloneReqVO planCloneReqVO) {
        planService.cloneTemplate(planCloneReqVO);
        return success(true);
    }

    @GetMapping("/queryPlanBindLand")
    @ApiOperation("查询计划已绑定地块")
    @PreAuthorize("@ss.hasPermission('plant:plan:query')")
    public CommonResult<List<PlanBindLandVO>> queryPlanBindLand() {
        List<PlanBindLandVO> planBindLandVOList = planService.queryPlanBindLand();
        return success(planBindLandVOList);
    }

    @PutMapping("/planPublish")
    @ApiOperation("种植计划发布或取消发布")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "status", value = "status 状态（0已发布 1未发布）", required = true, example = "1024", dataTypeClass = Integer.class)
    })
    @PreAuthorize("@ss.hasPermission('plant:plan:update')")
    public CommonResult<Boolean> planPublish(@RequestParam("id") Integer id, @RequestParam("status") Integer status) {
        planService.planPublish(id, status);
        return success(true);
    }

    @GetMapping("/queryLandPlan")
    @ApiOperation("查询地块计划")
    @PreAuthorize("@ss.hasPermission('plant:plan:query')")
    public CommonResult<List<PlanBindLandVO>> queryLandPlan() {
        List<PlanBindLandVO> planBindLandVOList = planService.queryLandPlan();
        return success(planBindLandVOList);
    }

    /**
     @PutMapping("/landBindPlan")
     @ApiOperation("地块绑定种植计划")
     @ApiImplicitParams({
     @ApiImplicitParam(name = "planId", value = "种植计划编号", required = true, example = "1024", dataTypeClass = Integer.class),
     @ApiImplicitParam(name = "landId", value = "地块编号", required = true, example = "1024", dataTypeClass = Integer.class)
     })
     @PreAuthorize("@ss.hasPermission('plant:plan:update')") public CommonResult<Boolean> landBindPlan(@RequestParam("planId") Integer planId, @RequestParam("landId") Integer landId) {
     planService.landBindPlan(planId,landId);
     return success(true);
     }
     */

}
