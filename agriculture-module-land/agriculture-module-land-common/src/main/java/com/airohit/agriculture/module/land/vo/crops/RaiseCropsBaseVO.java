package com.airohit.agriculture.module.land.vo.crops;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RaiseCropsBaseVO {

    @ApiModelProperty(value = "种植作物编号", required = true)
    private Integer id;

    @ApiModelProperty(value = "种植作物", required = true)
    private String cropsName;

    @ApiModelProperty(value = "作物图标", required = true)
    private String imageUrl;

    @ApiModelProperty(value = "编码", required = true)
    private String code;
}
