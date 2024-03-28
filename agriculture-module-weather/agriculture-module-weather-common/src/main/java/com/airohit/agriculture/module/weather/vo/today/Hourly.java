package com.airohit.agriculture.module.weather.vo.today;

import lombok.Data;

@Data
public class Hourly {

    private String text;

    private String code;

    private String temp_fc;

    private String wind_speed;

    private String wind_dir;

    private String prec_1h;

}
