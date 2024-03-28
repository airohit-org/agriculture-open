package com.airohit.agriculture.module.device.vo.obs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/26 15:05
 */
@Data
@ApiModel("设备分组统计信息")
public class DeviceGroupVo {
    @ApiModelProperty("设备名称")
    private String deviceName;
    @ApiModelProperty("数量")
    private Integer count;
    @ApiModelProperty("设备ID")
    private String deviceType;
}
