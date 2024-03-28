package com.airohit.agriculture.module.weather.dal.dataobject.weather;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.sql.Time;
import java.util.Date;


/**
 * 天气雷达
 * <p>
 * 此 @Document 用来指定索引, 也可以用来自动创建索引,
 * 一般而言, 索引是自己手动创建
 */
@TableName("weather_radar")
@KeySequence("weather_radar_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherRadar {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long tenantId;

    private String img;

    private String minLonlat;

    private String maxLonlat;

    private Date dateTime;

    private Date date;

    private Time time;

    private Date createDate;

    private Long farmTenantId;


}
