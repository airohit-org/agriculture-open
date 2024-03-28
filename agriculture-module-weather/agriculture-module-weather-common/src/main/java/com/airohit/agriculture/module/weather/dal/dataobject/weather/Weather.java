package com.airohit.agriculture.module.weather.dal.dataobject.weather;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;


/**
 * 天气
 * <p>
 * 此 @Document 用来指定索引, 也可以用来自动创建索引,
 * 一般而言, 索引是自己手动创建
 */
@TableName("weather")
@KeySequence("weather_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Weather {

    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long tenantId;

    private String cityName;

    private String weatherCode;

    private String weatherName;

    private String minimumTemperature;

    private String maximumTemperature;

    private String temperature;

    private String windSpeed;

    private String humidity;

    private String windDirection;

    private String rainfall;

    private String carbonDioxideConcentration;

    private Date weatherDate;

    private Long farmTenantId;

}
