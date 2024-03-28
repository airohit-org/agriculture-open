package com.airohit.agriculture.module.land.vo.temperaturezone;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@ApiModel("管理后台 - 积温带管理 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TemperatureZoneRespVO extends TemperatureZoneBaseVO {

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
