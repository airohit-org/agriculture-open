package com.airohit.agriculture.module.land.vo.crops;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CropsVarietiesBaseVO {

    @ApiModelProperty(value = "种植品种编号", required = true)
    private Integer id;

    @ApiModelProperty(value = "种植品种名称", required = true)
    private String cropsVarietiesName;

    @ApiModelProperty(value = "编码", required = true)
    private String code;
}
