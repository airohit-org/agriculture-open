package com.airohit.agriculture.module.device.vo.info;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 设备基本信息创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeviceInfoCreateReqVO extends DeviceInfoBaseVO {
    @ApiModelProperty(value = "农场ID", hidden = true)
    private Long farmTenantId;
}
