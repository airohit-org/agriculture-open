package com.airohit.agriculture.module.device.obs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("设备中心 - 修改 Request Dto")
@Data
@ToString(callSuper = true)
public class ObsDeviceUpdateDto {

    @ApiModelProperty("设备id")
    @NotNull
    private Integer id;

    @ApiModelProperty("设备类型")
    private String deviceType;

    @ApiModelProperty("设备名称")
    private String deviceName;

    @ApiModelProperty("地块id")
    private Integer landId;

    @ApiModelProperty("经度")
    private String devicelng;

    @ApiModelProperty("纬度")
    private String devicelat;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("sim卡号")
    private String simNumber;

    @ApiModelProperty("设备服务ip")
    private String deviceServiceIp;

    @ApiModelProperty("设备服务端口")
    private Integer deviceServicePort;

    @ApiModelProperty("设备服务模版")
    private String deviceServiceTemplate;
}
