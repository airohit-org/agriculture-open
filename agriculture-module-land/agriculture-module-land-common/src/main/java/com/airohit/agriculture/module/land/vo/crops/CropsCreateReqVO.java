package com.airohit.agriculture.module.land.vo.crops;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 地块作物创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CropsCreateReqVO extends CropsBaseVO {

    @ApiModelProperty(value = "种植作物名称")
    private String cropsName;

    @ApiModelProperty(value = "作物品种名称")
    private String cropsTypeName;
}
