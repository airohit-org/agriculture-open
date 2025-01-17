package com.airohit.agriculture.module.device.obs;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("厂商管理 - 厂商信息增加")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ObsDeviceFirmCreateDto extends ObsDeviceFirmBaseDto {

}
