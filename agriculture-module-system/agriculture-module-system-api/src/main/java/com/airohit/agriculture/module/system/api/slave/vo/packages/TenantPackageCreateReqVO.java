package com.airohit.agriculture.module.system.api.slave.vo.packages;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 租户套餐创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TenantPackageCreateReqVO extends TenantPackageBaseVO {

}
