package com.airohit.agriculture.module.plant.controller.app.plan;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.convert.plan.PlanConvert;
import com.airohit.agriculture.module.plant.dal.dataobject.plan.PlanDO;
import com.airohit.agriculture.module.plant.service.plan.PlanService;
import com.airohit.agriculture.module.plant.vo.plan.PlanPageReqVO;
import com.airohit.agriculture.module.plant.vo.plan.PlanRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Api(tags = "APP - 种植计划")
@RestController
@RequestMapping("/plant/plan")
@Validated
public class PlanController {

    @Resource
    private PlanService planService;

    @GetMapping("/get")
    @ApiOperation("获得种植计划")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    public CommonResult<PlanRespVO> getPlan(@RequestParam("id") Integer id) {
        PlanRespVO planRespVO = planService.getPlanById(id);
        return success(planRespVO);
    }

    @GetMapping("/page")
    @ApiOperation("获得种植计划分页")
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
}
