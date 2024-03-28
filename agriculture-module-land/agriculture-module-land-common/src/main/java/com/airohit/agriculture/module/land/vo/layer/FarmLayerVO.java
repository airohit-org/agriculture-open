package com.airohit.agriculture.module.land.vo.layer;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FarmLayerVO {
    /**
     * 编号
     */
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
    private Double growth1;

    /**
     * 图层统计信息（长势2级）
     */
    private Double growth2;

    /**
     * 图层统计信息（长势3级）
     */
    private Double growth3;

    /**
     * 图层统计信息（长势4级）
     */
    private Double growth4;

    /**
     * 图层统计信息（长势5级）
     */
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
