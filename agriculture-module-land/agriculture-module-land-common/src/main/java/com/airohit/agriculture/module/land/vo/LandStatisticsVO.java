package com.airohit.agriculture.module.land.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LandStatisticsVO {
    @ApiModelProperty(value = "地块数量")
    private Integer landCount;

    @ApiModelProperty(value = "地块总亩数")
    private Double landAreaCount;
}
