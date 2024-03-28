package com.airohit.agriculture.module.device.vo.obs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/*
 * @Description:
 * @Author: hanliyao
 * @Date: 2023/7/13 15:23
 */
@Data
public class ObsDeviceVo {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("厂商id")
    private Integer firmId;
    @ApiModelProperty("设备厂商")
    private String deviceFirm;
    @ApiModelProperty("设备地址(设备编号)")
    private String deviceAddr;
    @ApiModelProperty("区域id")
    private String groupId;
    @ApiModelProperty("农场id")
    private Integer farmId;
    @ApiModelProperty("农场名称")
    private String farmName;
    @ApiModelProperty("设备类型")
    private String deviceType;
    @ApiModelProperty("地块id")
    private Integer landId;
    @ApiModelProperty("地块名称")
    private String landName;
    @ApiModelProperty("设备名称")
    private String deviceName;
    @ApiModelProperty("经度")
    private double devicelng;

    @ApiModelProperty("纬度")
    private double devicelat;

    @ApiModelProperty("设备状态(online 在线,offline 离线,alarm 告警)")
    private String status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("设备服务ip")
    private String deviceServiceIp;

    @ApiModelProperty("设备服务端口")
    private Integer deviceServicePort;

    @ApiModelProperty("设备服务模版")
    private String deviceServiceTemplate;
}
