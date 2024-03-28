package com.airohit.agriculture.module.plant.vo.planTemplate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 计划模版创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PlanTemplateCreateReqVO extends PlanTemplateBaseVO {

    @ApiModelProperty(value = "状态（0已发布 1未发布）")
    private Integer status;

}
