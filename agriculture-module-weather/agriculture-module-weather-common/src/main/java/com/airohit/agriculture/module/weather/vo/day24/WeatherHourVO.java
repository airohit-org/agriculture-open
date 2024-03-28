package com.airohit.agriculture.module.weather.vo.day24;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class WeatherHourVO {

    private Long id;

    @ApiModelProperty("天气气象编码")
    private String code;

    @ApiModelProperty("天气气象")
    private String text;

    @ApiModelProperty("大气温度")
    private String tempFc;

    @ApiModelProperty("相对湿度 %")
    private String rh;

    @ApiModelProperty("风向")
    private String windDir;

    @ApiModelProperty("风速 m/s")
    private String windSpeed;

    @ApiModelProperty("几级风")
    private String windClass;

    @ApiModelProperty("年月日时")
    private String dataTime;

    @ApiModelProperty("年月日")
    private String date;

    @ApiModelProperty("时")
    private String time;

    private String tenantId;

    private String farmTenantId;
}
