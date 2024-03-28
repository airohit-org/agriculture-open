package com.airohit.agriculture.module.weather.vo.grid;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class WeatherGridVO {

    private Long id;

    @ApiModelProperty("地块编号")
    private Long landId;

    @ApiModelProperty("天气气象编码")
    private String code;

    @ApiModelProperty("天气气象")
    private String text;

    @ApiModelProperty("温度")
    private String temp;

    @ApiModelProperty("经纬度")
    private String lonlat;

    @ApiModelProperty("体感温度")
    private String feelsLike;

    @ApiModelProperty("相对湿度")
    private String rh;

    @ApiModelProperty("风级")
    private String windClass;

    @ApiModelProperty("风速")
    private String windSpeed;

    @ApiModelProperty("风向")
    private String windDir;

    @ApiModelProperty("风象角")
    private String windAngle;

    @ApiModelProperty("云量")
    private String clouds;

    @ApiModelProperty("水平浓见度")
    private String vis;

    @ApiModelProperty("气压")
    private String pressure;

    @ApiModelProperty("露点温度")
    private String dew;

    @ApiModelProperty("紫外线指数")
    private String uv;

    @ApiModelProperty("降水量")
    private String prec;

    @ApiModelProperty("租户编号")
    private String tenantId;

    @ApiModelProperty("创建日期")
    private String createDate;

    private String farmTenantId;
}
