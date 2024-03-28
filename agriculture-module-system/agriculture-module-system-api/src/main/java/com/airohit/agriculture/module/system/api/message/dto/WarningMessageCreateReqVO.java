package com.airohit.agriculture.module.system.api.message.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 预警消息创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WarningMessageCreateReqVO extends WarningMessageBaseVO {
    private Long tenantId;

}
