package com.airohit.agriculture.module.plant.vo.planTemplate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@ApiModel("管理后台 - 计划模版 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PlanTemplateRespVO extends PlanTemplateBaseVO {

    @ApiModelProperty(value = "编号", required = true)
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
