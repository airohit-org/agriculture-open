package com.airohit.agriculture.module.plant.vo.taskTemplateInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 整地 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
@ApiModel("整地")
public class TaskTemplateRakingBaseVO {

    @ApiModelProperty(value = "任务基本信息id")
    private Integer agroTaskTemplateId;

    @ApiModelProperty(value = "深翻深度")
    private String turningOverDepth;

    @ApiModelProperty(value = "耙地深度")
    private String rakingDepth;

    @ApiModelProperty(value = "耙地次数")
    private Integer rakingTimes;

    @ApiModelProperty(value = "旋地深度")
    private String gyrationDepth;

    @ApiModelProperty(value = "旋转次数")
    private Integer gyrationTimes;

    @ApiModelProperty(value = "农机")
    private String farmMachinery;

}
