package com.airohit.agriculture.module.statistics.controller.admin.statistics;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.statistics.service.farm.FarmService;
import com.airohit.agriculture.module.statistics.service.message.WarningMessageService;
import com.airohit.agriculture.module.statistics.service.taskInfo.StatisticTaskInfoService;
import com.airohit.agriculture.module.statistics.vo.farm.FarmStatisticVo;
import com.airohit.agriculture.module.statistics.vo.message.MessageStatisticVo;
import com.airohit.agriculture.module.statistics.vo.taskInfo.TaskInfoStatusVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/17 15:52
 */
@Api(tags = "管理后台 - 统计服务")
@RestController
@RequestMapping("/statistics")
@Slf4j
public class StatisticsController {
    @Resource
    private StatisticTaskInfoService statisticTaskInfoService;
    @Resource
    private WarningMessageService warningMessageService;
    @Resource
    private FarmService farmService;

    @GetMapping("/getTaskInfoStatistic")
    @ApiOperation("获得农事任务统计")
    @PermitAll
    public CommonResult<List<TaskInfoStatusVo>> getTaskInfoStatistic() {
        return success(statisticTaskInfoService.getTaskInfoStatistic());
    }


    @GetMapping("/warningMessageStatistic")
    @ApiOperation("获得预警消息统计")
    @PermitAll
    public CommonResult<List<MessageStatisticVo>> getMessageStatisticVoList(@RequestParam("limit") @ApiParam("条数") Integer limit) {
        return success(warningMessageService.getMessageStatisticVoList(limit));
    }

    @GetMapping("/getFarmStatisticVo")
    @ApiOperation("获得农场信息概览")
    @PermitAll
    public CommonResult<FarmStatisticVo> getFarmStatisticVo() {
        return success(farmService.getFarmStatisticVo());
    }

    @GetMapping("/getFarmInfos")
    @ApiOperation("获得农场信息")
    @PermitAll
    public CommonResult<List<Long>> getFarmInfos() {
        return success(farmService.getFarmInfos());
    }







}
