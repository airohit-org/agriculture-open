package com.airohit.agriculture.module.device.vo.obs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/*
 * @Description:
 * @Author: hanliyao
 * @Date: 2023/7/13 15:23
 */
@Data
public class ObsDeviceTypeVo {
    @ApiModelProperty("设备名称")
    private String deviceName;
    @ApiModelProperty("全部数量")
    private Integer allNum;
    @ApiModelProperty("在线数量")
    private Integer onlineNum;
    @ApiModelProperty("离线数量")
    private Integer offlineNum;
}
