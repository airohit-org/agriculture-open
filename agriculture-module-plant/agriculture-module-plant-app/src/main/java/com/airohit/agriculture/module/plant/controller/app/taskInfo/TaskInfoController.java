package com.airohit.agriculture.module.plant.controller.app.taskInfo;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.service.taskInfo.TaskInfoService;
import com.airohit.agriculture.module.plant.vo.taskInfo.TaskInfoPageReqVo;
import com.airohit.agriculture.module.plant.vo.taskInfo.TaskInfoRespVo;
import com.airohit.agriculture.module.plant.vo.taskInfo.TaskInfoUpdateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;
import static com.airohit.agriculture.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/4 13:32
 */
@Api(tags = "APP - 农事任务")
@RestController
@RequestMapping("/plant/app/taskInfo")
@Validated
public class TaskInfoController {

    @Resource
    private TaskInfoService taskInfoService;

    @GetMapping("/getTaskInfoRespVoById")
    @ApiOperation("获取农事任务详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "农事任务ID", example = "1", required = true, dataTypeClass = Integer.class),
    })
    public CommonResult<TaskInfoRespVo> getTaskInfoRespVoById(@RequestParam("id") Integer id) {
        return success(taskInfoService.getTaskInfoRespVoById(id));
    }


    @PostMapping("/updateTask")
    @ApiOperation("提交农事任务")
    public CommonResult<Boolean> updateTask(@RequestBody TaskInfoUpdateVO taskInfoUpdateVO) {
        taskInfoService.updateTaskInfoApp(taskInfoUpdateVO);
        return success(true);
    }

    @GetMapping("/page")
    @ApiOperation("获得农事任务分页")
    public CommonResult<PageResult<TaskInfoRespVo>> page(@Valid TaskInfoPageReqVo pageVO) {
        pageVO.setPeasantId(getLoginUserId());
        PageResult<TaskInfoRespVo> pageResult = taskInfoService.getTaskInfoPage(pageVO);
        return success(pageResult);
    }
}
