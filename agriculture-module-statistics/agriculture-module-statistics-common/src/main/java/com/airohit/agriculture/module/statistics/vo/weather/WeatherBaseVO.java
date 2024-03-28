package com.airohit.agriculture.module.statistics.vo.weather;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherBaseVO {
    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("天气编码")
    private String weatherCode;

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

    @ApiModelProperty("10天气数据集合")
    private List<WeatherDays> weatherDays;

    @ApiModelProperty("日期")
    private String date;

}
