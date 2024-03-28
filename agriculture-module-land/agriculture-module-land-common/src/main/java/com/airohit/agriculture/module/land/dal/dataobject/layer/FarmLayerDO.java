package com.airohit.agriculture.module.land.dal.dataobject.layer;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 种植作物 DO
 *
 * @author shiminghao
 */
@TableName("farm_layer")
@KeySequence("farm_layer_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FarmLayerDO {

    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 租户编码
     */
    private Integer tenantId;

    /**
     * 农场编码
     */
    private Integer farmId;

    /**
     * 图层名称
     */
    private String lyrName;

    /**
     * 服务链接
     */
    private String url;

    /**
     * 图层统计信息（长势1级）
     */
    @TableField("growth_1")
    private Double growth1;

    /**
     * 图层统计信息（长势2级）
     */
    @TableField("growth_2")
    private Double growth2;

    /**
     * 图层统计信息（长势3级）
     */
    @TableField("growth_3")
    private Double growth3;

    /**
     * 图层统计信息（长势4级）
     */
    @TableField("growth_4")
    private Double growth4;

    /**
     * 图层统计信息（长势5级）
     */
    @TableField("growth_5")
    private Double growth5;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 数据时间
     */
    private LocalDateTime dataTime;


}
