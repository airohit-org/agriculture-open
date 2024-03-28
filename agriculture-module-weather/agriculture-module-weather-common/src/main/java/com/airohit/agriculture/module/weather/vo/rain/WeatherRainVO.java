package com.airohit.agriculture.module.weather.vo.rain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class WeatherRainVO {

    private Long id;

    @ApiModelProperty("天气气象编码")
    private String code;

    @ApiModelProperty("天气气象")
    private String text;

    @ApiModelProperty("天气提示")
    private String msg;

    @ApiModelProperty("经纬度")
    private String lonlat;

    @ApiModelProperty("大气温度")
    private String tempFc;

    @ApiModelProperty("降水量 ml")
    private String rain;

    @ApiModelProperty("年月日时")
    private String dataTime;

    @ApiModelProperty("年月日")
    private String date;

    @ApiModelProperty("时")
    private String time;

    private String tenantId;

    private String farmTenantId;
}
