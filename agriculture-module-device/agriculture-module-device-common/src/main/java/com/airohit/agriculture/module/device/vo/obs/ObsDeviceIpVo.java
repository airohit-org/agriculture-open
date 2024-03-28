package com.airohit.agriculture.module.device.vo.obs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/*
 * @Description:
 * @Author: hanliyao
 * @Date: 2023/7/13 15:23
 */
@Data
public class ObsDeviceIpVo {
    @ApiModelProperty("设备服务ip")
    private String deviceServiceIp;

    @ApiModelProperty("设备服务端口")
    private Integer deviceServicePort;

    @ApiModelProperty("设备服务模版")
    private String deviceServiceTemplate;
}
