package com.airohit.agriculture.module.plant.controller.admin.taskInfo;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.service.taskInfo.TaskInfoService;
import com.airohit.agriculture.module.plant.vo.taskInfo.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/4 13:32
 */
@Api(tags = "管理后台 - 农事任务")
@RestController
@RequestMapping("/plant/taskInfo")
@Validated
public class TaskInfoController {

    @Resource
    private TaskInfoService taskInfoService;

    @GetMapping("/getTaskFiled")
    @ApiOperation("获得农事任务提交表单模版")
    @PermitAll
    @ApiImplicitParam(name = "typeTableName", value = "类型名称", required = true, example = "1024", dataTypeClass = String.class)
    public CommonResult<JSONArray> getTaskFiled(@RequestParam("typeTableName") String typeTableName) {

        return success(taskInfoService.getTaskFiled(typeTableName));
    }

    @PostMapping("/createTask")
    @ApiOperation("创建农事任务")
    @PreAuthorize("@ss.hasPermission('plant:taskInfo:create')")
    public CommonResult<Boolean> createTask(@RequestBody JSONObject jsonObject) {
        taskInfoService.createTask(jsonObject);
        return success(Boolean.TRUE);
    }

    @GetMapping("/getTaskInfoBaseVOListByDate")
    @ApiOperation("获取日历数据列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "年月", example = "2023-04", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "plantingPlanId", value = "种植计划ID", required = true, dataTypeClass = Integer.class)
    })
    @PermitAll
    public CommonResult<List<TaskInfoBaseVO>> getTaskInfoBaseVOListByDate(@RequestParam("date") String date, @RequestParam("plantingPlanId") Integer plantingPlanId) {

        return success(taskInfoService.getTaskInfoBaseVOListByDate(date, plantingPlanId));
    }

    @GetMapping("/getTaskDetail")
    @ApiOperation("获取农事任务详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "农事任务ID", example = "1", required = true, dataTypeClass = Integer.class),
    })
    @PreAuthorize("@ss.hasPermission('plant:task:query')")
    public CommonResult<JSONObject> getTaskDetail(@RequestParam("id") Integer id) {

        return success(taskInfoService.getTaskDetail(id));
    }

    @DeleteMapping("/deleteTaskInfo")
    @ApiOperation("删除农事任务")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('plant:task:delete')")
    public CommonResult<Boolean> deleteTaskInfo(@RequestParam("id") Integer id) {
        taskInfoService.delete(id);
        return success(true);
    }

    @PutMapping("/updateTaskDetail")
    @ApiOperation("更新农事任务")
    @PreAuthorize("@ss.hasPermission('plant:task:update')")
    public CommonResult<Boolean> updateTaskDetail(@RequestBody JSONObject jsonObject) {
        taskInfoService.updateTaskDetail(jsonObject);
        return success(true);
    }

    @PutMapping("/updateTaskAppoint")
    @ApiOperation("指派农事任务")
    @PreAuthorize("@ss.hasPermission('plant:task:appoint')")
    public CommonResult<Boolean> updateTaskAppoint(@RequestBody TaskAppointVO taskAppointVO) {
        taskInfoService.updateTaskAppoint(taskAppointVO);
        return success(true);
    }

    @PutMapping("/updateTaskAppointList")
    @ApiOperation("批量指派农事任务")
    @PreAuthorize("@ss.hasPermission('plant:task:appoint')")
    public CommonResult<Boolean> updateTaskAppointList(@RequestBody TaskAppointListVO taskAppointVO) {
        taskInfoService.updateTaskAppointList(taskAppointVO);
        return success(true);
    }

    @GetMapping("/page")
    @ApiOperation("获得农事任务分页")
    @PreAuthorize("@ss.hasPermission('plant:task:page')")
    public CommonResult<PageResult<TaskInfoRespVo>> page(@Valid TaskInfoPageReqVo pageVO) {
        PageResult<TaskInfoRespVo> pageResult = taskInfoService.getTaskInfoPage(pageVO);
        return success(pageResult);
    }
}
