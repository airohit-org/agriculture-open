package com.airohit.agriculture.module.plant.vo.plan;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@ApiModel("管理后台 - 种植计划 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PlanRespVO extends PlanBaseVO {

    @ApiModelProperty(value = "编号", required = true)
    private Integer id;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

    @ApiModelProperty(value = "作物名称", required = true)
    private String cropsName;

    @ApiModelProperty(value = "作物图片", required = true)
    private String imageUrl;

    @ApiModelProperty(value = "作物品种名称", required = true)
    private String cropsTypeName;

    @ApiModelProperty(value = "地块名称", required = true)
    private String landName;

}
