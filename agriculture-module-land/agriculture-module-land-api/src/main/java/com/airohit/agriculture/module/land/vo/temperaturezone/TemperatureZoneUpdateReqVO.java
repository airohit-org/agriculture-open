package com.airohit.agriculture.module.land.vo.temperaturezone;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 积温带管理更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TemperatureZoneUpdateReqVO extends TemperatureZoneBaseVO {


    @ApiModelProperty(value = "区域")
    private String region;

}
