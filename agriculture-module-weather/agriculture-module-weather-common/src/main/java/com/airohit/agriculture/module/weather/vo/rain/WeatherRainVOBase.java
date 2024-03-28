package com.airohit.agriculture.module.weather.vo.rain;

import lombok.Data;

@Data
public class WeatherRainVOBase {

    //@ApiModelProperty("天气气象编码")
    private String code;

    //@ApiModelProperty("天气气象")
    private String text;

    //@ApiModelProperty("天气提示")
    private String msg;

}
