package com.airohit.agriculture.module.weather.dal.dataobject.weather;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;


/**
 * 天气1公里网格
 * <p>
 * 此 @Document 用来指定索引, 也可以用来自动创建索引,
 * 一般而言, 索引是自己手动创建
 */
@TableName("weather_grid")
@KeySequence("weather_grid_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherGrid {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long tenantId;

    private Long landId;

    private String code;

    private String text;

    private String temp;

    private String lonlat;

    private String feelsLike;

    private String rh;

    private String windClass;

    private String windSpeed;

    private String windDir;

    private String windAngle;

    private String clouds;

    private String vis;

    private String pressure;

    private String dew;

    private String uv;

    private String prec;

    private Date createDate;

    private String farmTenantId;

}
