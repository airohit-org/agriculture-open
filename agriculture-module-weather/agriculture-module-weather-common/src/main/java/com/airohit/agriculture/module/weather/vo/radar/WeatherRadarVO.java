package com.airohit.agriculture.module.weather.vo.radar;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class WeatherRadarVO {

    private Long id;

    @ApiModelProperty("oss图片地址")
    private String img;

    @ApiModelProperty("最小经纬度")
    private String minLonlat;

    @ApiModelProperty("最大经纬度")
    private String maxLonlat;

    @ApiModelProperty("年月日时")
    private String dateTime;

    @ApiModelProperty("年月日")
    private String date;

    @ApiModelProperty("时")
    private String time;

    private String tenantId;

    private String farmTenantId;

}
