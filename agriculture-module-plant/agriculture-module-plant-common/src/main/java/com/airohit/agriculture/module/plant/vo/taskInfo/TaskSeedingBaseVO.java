package com.airohit.agriculture.module.plant.vo.taskInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 播种 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
@ApiModel("播种")
public class TaskSeedingBaseVO {

    @ApiModelProperty(value = "任务基本信息id")
    private Integer agroTaskId;

    @ApiModelProperty(value = "播种方式")
    private String seedingMethod;

    @ApiModelProperty(value = "株距")
    private String rowSpacing;

    @ApiModelProperty(value = "行距")
    private String arrayPitch;

    @ApiModelProperty(value = "亩用种量")
    private String seedUsage;

    @ApiModelProperty(value = "农机")
    private String farmMachinery;

}
