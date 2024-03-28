package com.airohit.agriculture.module.weather.dal.dataobject.weather;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 农场 DO
 *
 * @author shiminghao
 */
@TableName("farm")
@KeySequence("farm_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class FarmDO extends BaseDO {

    /**
     * Id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 农场名称
     */
    private String farmName;
    /**
     * 种植面积
     */
    private String plantArea;
    /**
     * 联系人
     */
    private String contacts;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String area;
    /**
     * 状态（0正常 1关闭）
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 坐标
     */
    private String coordinate;
    /**
     * 中心点
     */
    private String coordinateCenter;
    /**
     * 颜色
     */
    private String color;

    /**
     * 地块分割url
     */
    private String landSegUrl;
    /**
     * 长势分析url
     */
    private String growthAnalysis;
    /**
     * 积温带
     */
    private String accumulatedTemperatureZone;

}
