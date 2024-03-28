package com.airohit.agriculture.module.statistics.vo.taskInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/6 13:42
 */
@Data
@ApiModel("农事任务列表")
public class TaskInfoRespVo {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "任务名称")
    private String agroName;

    @ApiModelProperty(value = "开始日期")
    private LocalDateTime startDate;

    @ApiModelProperty(value = "任务周期")
    private Integer taskPeriod;

    @ApiModelProperty(value = "任务类型")
    private Integer type;

    @ApiModelProperty("任务状态,1未指派,2未开始,3进行中,4未完成,5已完成")
    private Integer taskStatus;

    @ApiModelProperty("农户ID")
    private Integer peasantId;

    @ApiModelProperty("任务是否提交")
    private Integer status;
}
