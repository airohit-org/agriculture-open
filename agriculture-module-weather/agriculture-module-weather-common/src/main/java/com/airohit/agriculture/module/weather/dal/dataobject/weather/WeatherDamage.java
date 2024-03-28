package com.airohit.agriculture.module.weather.dal.dataobject.weather;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;


/**
 * 天气预警
 * <p>
 * 此 @Document 用来指定索引, 也可以用来自动创建索引,
 * 一般而言, 索引是自己手动创建
 */
@TableName("weather_damage")
@KeySequence("weather_damage_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDamage {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long tenantId;

    private String lonlat;

    private String effective;

    private String expires;

    private String type;

    private String typeId;

    private Date date;

    private String title;

    private String desc;

    private String publicTime;

    private Long hour;

    private Date createDate;

    private Long farmTenantId;

}
