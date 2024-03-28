package com.airohit.agriculture.module.weather.vo.damage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class WeatherDamageVO {

    private Long id;

    @ApiModelProperty("经纬度")
    private String lonlat;

    @ApiModelProperty("预警生效时间")
    private String effective;

    @ApiModelProperty("预警失效时间")
    private String expires;

    @ApiModelProperty("预警类型")
    private String type;

    @ApiModelProperty("预警类型id")
    private String typeId;

    @ApiModelProperty("日期")
    private String date;

    private String tenantId;

    @ApiModelProperty("摘要")
    private String title;

    @ApiModelProperty("详情")
    private String desc;

    @ApiModelProperty("发布时间")
    private String publicTime;

    @ApiModelProperty("持续时间")
    private Long hour;

    private String createDate;

    private String farmTenantId;
}
