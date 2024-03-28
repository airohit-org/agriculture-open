package com.airohit.agriculture.module.statistics.vo.weather;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WeatherDays {

    @ApiModelProperty("最低温度")
    private String minimumTemperature;

    @ApiModelProperty("最高温度")
    private String maximumTemperature;

    @ApiModelProperty("日期")
    private String date;

}
