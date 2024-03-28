package com.airohit.agriculture.module.system.api.slave.vo.farm;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 农场 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class FarmBaseVO {

    @ApiModelProperty(value = "农场名称")
    private String farmName;

    @ApiModelProperty(value = "种植面积", required = true)
    private String plantArea;

    @ApiModelProperty(value = "联系人", required = true)
    private String contacts;

    @ApiModelProperty(value = "详细地址", required = true)
    private String address;

    @ApiModelProperty(value = "联系电话", required = true)
    private String tel;

    @ApiModelProperty(value = "省", required = true)
    private String province;

    @ApiModelProperty(value = "市", required = true)
    private String city;

    @ApiModelProperty(value = "区/县", required = true)
    private String area;

    /**
     * 坐标
     */
    @ApiModelProperty(value = "坐标", required = true)
    private String coordinate;
    /**
     * 中心点
     */
    @ApiModelProperty(value = "中心点", required = true)
    private String coordinateCenter;
    /**
     * 颜色
     */
    @ApiModelProperty(value = "颜色", required = true)
    private String color;

    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID", required = true)
    private Long tenantId;

    /**
     * 地块分割url
     */
    @ApiModelProperty(value = "地块分割url")
    private String landSegUrl;
    /**
     * 长势分析url
     */
    @ApiModelProperty(value = "长势分析url")
    private String growthAnalysis;

    /**
     * 积温带
     */
    @ApiModelProperty(value = "积温带")
    private String accumulatedTemperatureZone;

}
