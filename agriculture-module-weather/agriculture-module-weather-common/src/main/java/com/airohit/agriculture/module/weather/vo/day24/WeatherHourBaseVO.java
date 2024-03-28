package com.airohit.agriculture.module.weather.vo.day24;

import lombok.Data;

@Data
public class WeatherHourBaseVO {

    private String code;

    //@ApiModelProperty("天气气象")
    private String text;

    //@ApiModelProperty("大气温度")
    private String temp_fc;

    //@ApiModelProperty("相对湿度 %")
    private String rh;

    //@ApiModelProperty("风向")
    private String wind_dir;

    //@ApiModelProperty("风速 m/s")
    private String wind_speed;

    //@ApiModelProperty("几级风")
    private String wind_class;

    //@ApiModelProperty("年月日时")
    private String data_time;

    //@ApiModelProperty("年月日")
    private String date;

    //@ApiModelProperty("时")
    private String time;


}
