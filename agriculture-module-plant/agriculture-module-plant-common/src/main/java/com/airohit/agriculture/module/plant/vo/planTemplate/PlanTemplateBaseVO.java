package com.airohit.agriculture.module.plant.vo.planTemplate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 计划模版 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class PlanTemplateBaseVO {

    @ApiModelProperty(value = "计划名称")
    private String planName;

    @ApiModelProperty(value = "种植作物")
    private String crops;

    @ApiModelProperty(value = "作物品种")
    private String cropsType;

    @ApiModelProperty(value = "开始日期")
    private Date startTime;

    @ApiModelProperty(value = "结束日期")
    private Date endTime;

    @ApiModelProperty(value = "1 管理员 2 用户")
    private Boolean type;

    @ApiModelProperty(value = "计划周期")
    private Integer planningCycle;

}
