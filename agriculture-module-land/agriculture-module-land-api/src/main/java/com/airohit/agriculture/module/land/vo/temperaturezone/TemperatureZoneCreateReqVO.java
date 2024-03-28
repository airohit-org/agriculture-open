package com.airohit.agriculture.module.land.vo.temperaturezone;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 积温带管理创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TemperatureZoneCreateReqVO extends TemperatureZoneBaseVO {

    @ApiModelProperty(value = "农场ID", hidden = true)
    private Long farmTenantId;

}
