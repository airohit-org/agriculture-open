package com.airohit.agriculture.module.land.vo.raise;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 种植作物创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RaiseCropsCreateReqVO extends RaiseCropsBaseVO {
    @ApiModelProperty(value = "农场ID", hidden = true)
    private Long farmTenantId;
}
