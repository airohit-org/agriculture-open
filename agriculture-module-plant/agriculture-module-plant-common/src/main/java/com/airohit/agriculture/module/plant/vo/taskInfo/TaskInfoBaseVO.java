package com.airohit.agriculture.module.plant.vo.taskInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 农事任务基本信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
@ApiModel("农事任务基本信息")
public class TaskInfoBaseVO {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "任务名称")
    private String agroName;

    @ApiModelProperty(value = "开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;

    @ApiModelProperty(value = "任务周期")
    private Integer taskPeriod;

    @ApiModelProperty(value = "任务类型")
    private Integer type;

    @ApiModelProperty(value = "任务对应的表名")
    private String typeTableName;

    @ApiModelProperty(value = "任务对应的模型名称")
    private String typeModelName;

    @ApiModelProperty(value = "计划阶段")
    private String planningStage;

    @ApiModelProperty(value = "种植计划模版ID")
    private Integer plantingPlanId;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "视频链接")
    private String videoUrl;

    @ApiModelProperty(value = "图片链接")
    private String imageUrls;

    @ApiModelProperty(value = "施肥")
    private TaskFertilizerBaseVO taskFertilizerBaseVO;

    @ApiModelProperty(value = "中耕")
    private TaskIntertillBaseVO taskIntertillBaseVO;

    @ApiModelProperty(value = "灌溉")
    private TaskIrrigationBaseVO taskIrrigationBaseVO;

    @ApiModelProperty("打药")
    private TaskPesticideBaseVO taskPesticideBaseVO;

    @ApiModelProperty("整地")
    private TaskRakingBaseVO taskRakingBaseVO;

    @ApiModelProperty("收割")
    private TaskReapBaseVO taskReapBaseVO;

    @ApiModelProperty("播种")
    private TaskSeedingBaseVO taskSeedingBaseVO;

    @ApiModelProperty("除草")
    private TaskWeedBaseVO taskWeedBaseVO;
}
