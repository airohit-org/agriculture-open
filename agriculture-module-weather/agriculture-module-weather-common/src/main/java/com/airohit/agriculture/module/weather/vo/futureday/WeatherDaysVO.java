package com.airohit.agriculture.module.weather.vo.futureday;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class WeatherDaysVO {

    private Long id;

    @ApiModelProperty("最低温度")
    private String minimumTemperature;

    @ApiModelProperty("最高温度")
    private String maximumTemperature;

    @ApiModelProperty("日期")
    private String weatherDate;

    private String tenantId;

    private String farmTenantId;
}
