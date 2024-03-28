package com.airohit.agriculture.module.plant.vo.taskTemplateInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 灌溉 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
@ApiModel("灌溉")
public class TaskTemplateIrrigationBaseVO {

    @ApiModelProperty(value = "任务基本信息id")
    private Integer agroTaskTemplateId;

    @ApiModelProperty(value = "灌溉方式")
    private String irrigationMethod;

    @ApiModelProperty(value = "灌溉量")
    private BigDecimal irrigationAmount;

    @ApiModelProperty(value = "农机")
    private String farmMachinery;

}
