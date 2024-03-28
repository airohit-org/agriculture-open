package com.airohit.agriculture.module.device.vo.info;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 设备基本信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DeviceInfoBaseVO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "设备型号")
    private String deviceType;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备描述")
    private String deviceDesc;

    @ApiModelProperty(value = "设备功能")
    private String deviceFunc;

    @ApiModelProperty(value = "样图")
    private String image;

    @ApiModelProperty(value = "设备厂商")
    private String deviceCompany;

    @ApiModelProperty(value = "设备本地通信方式")
    private String localCommunication;

    @ApiModelProperty(value = "设备本地通信协议")
    private String localCommunicationProtocol;

    @ApiModelProperty(value = "数据接入方式")
    private String accessMethod;

    @ApiModelProperty(value = "接入平台协议")
    private String localPlatformProrocol;


    @ApiModelProperty(value = "数据表示code", hidden = true)
    private String dataCode;

    @ApiModelProperty(value = "租户ID", hidden = true)
    private Long tenantId;

}
