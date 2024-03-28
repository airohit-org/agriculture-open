package com.airohit.agriculture.module.plant.vo.plan;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel("管理后台 - 种植计划创建 Request VO")
@Data
@ToString(callSuper = true)
public class PlanCloneReqVO {

    @ApiModelProperty(value = "计划编号", required = true)
    @NotNull(message = "计划编号不能为空")
    private Integer plantingPlanId;

    @ApiModelProperty(value = "计划名称", required = true)
    @NotNull(message = "计划名称不能为空")
    private String planName;

    @ApiModelProperty(value = "开始日期", required = true)
    @NotNull(message = "开始日期不能为空")
    private Date startTime;

    @ApiModelProperty(value = "1 管理员 2 用户", required = false)
    private Integer type;
}
