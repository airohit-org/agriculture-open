package com.airohit.agriculture.module.weather.vo.today;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class WeatherVO {

    private Long id;

    //private String cityName;

    @ApiModelProperty("天气编码")
    private String weatherCode;

    @ApiModelProperty("天气名称")
    private String weatherName;

    @ApiModelProperty("最低温度")
    private String minimumTemperature;

    @ApiModelProperty("最高温度")
    private String maximumTemperature;

    @ApiModelProperty("温度")
    private String temperature;

    @ApiModelProperty("风速")
    private String windSpeed;

    @ApiModelProperty("湿度")
    private String humidity;

    @ApiModelProperty("风向")
    private String windDirection;

    @ApiModelProperty("降雨量")
    private String rainfall;

    @ApiModelProperty("二氧化碳浓度")
    private String carbonDioxideConcentration;

    @ApiModelProperty("日期")
    private String weatherDate;

    private String tenantId;

    private String farmTenantId;

}
