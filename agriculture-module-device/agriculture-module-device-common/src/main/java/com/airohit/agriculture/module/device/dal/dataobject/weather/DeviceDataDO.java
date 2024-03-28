package com.airohit.agriculture.module.device.dal.dataobject.weather;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 天气设备数据 DO
 *
 * @author 管理员
 */
@TableName("weather_device_data")
@KeySequence("weather_device_data_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("天气设备数据")
public class DeviceDataDO extends BaseDO {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 温度
     */
    @ApiModelProperty("温度,单位:℃")
    private Double temp;
    /**
     * 湿度
     */
    @ApiModelProperty("湿度,单位:%RH")
    private Double rh;
    /**
     * 光照强度
     */
    @ApiModelProperty("光照强度,单位:Lux")
    private Double li;
    /**
     * 太阳总辐射
     */
    @ApiModelProperty("太阳总辐射,单位:W/㎡")
    private Double tsr;
    /**
     * 光合有效辐射
     */
    @ApiModelProperty("光合有效辐射,单位:μmol/㎡·s")
    private Double par;
    /**
     * 紫外线强度
     */
    @ApiModelProperty("紫外线强度,单位:级（mW/ cm2）")
    private Double uv;
    /**
     * co2含量
     */
    @ApiModelProperty("co2含量,单位:ppm")
    private Double co2;
    /**
     * 风速
     */
    @ApiModelProperty("风速,单位:m/s")
    private Double windSpeed;
    /**
     * 风力
     */
    @ApiModelProperty("风力,单位:级")
    private String windClass;
    /**
     * 风向
     */
    @ApiModelProperty("风向,单位:无")
    private String windDir;
    /**
     * 瞬时降水量
     */
    @ApiModelProperty("瞬时降水量,单位:mm")
    private Double rain;
    /**
     * 当前降水量
     */
    @ApiModelProperty("当前降水量,单位:mm")
    private Double todayRain;
    /**
     * 昨日降水量
     */
    @ApiModelProperty("昨日降水量,单位:mm")
    private Double yesterdayRain;
    /**
     * 累积降雨量
     */
    @ApiModelProperty("累积降雨量,单位:mm")
    private Double allRain;
    /**
     * 蒸发
     */
    @ApiModelProperty("蒸发,单位:mm")
    private Double evaporation;
    /**
     * 噪声
     */
    @ApiModelProperty("噪声,单位:dB")
    private Double noise;
    /**
     * 气压
     */
    @ApiModelProperty("气压,单位:Kpa")
    private Double pressure;
    /**
     * pm2.5
     */
    @ApiModelProperty("pm2.5,单位:ug/m3")
    private Double pm25;
    /**
     * pm10
     */
    @ApiModelProperty("pm10,单位:ug/m3")
    private Double pm10;
    /**
     * 土壤表层温度
     */
    @ApiModelProperty("土壤表层温度,单位:℃")
    private Double st;
    /**
     * 土壤表层湿度
     */
    @ApiModelProperty("土壤表层湿度,单位:%")
    private Double srh;
    /**
     * 土壤表层电导率
     */
    @ApiModelProperty("土壤表层电导率,单位:us/cm")
    private Double ssc;
    /**
     * 备注
     */
    private String remark;

}
