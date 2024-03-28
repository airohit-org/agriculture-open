package com.airohit.agriculture.module.device.vo.farmyun;

import lombok.Data;

/**
 * Created with IDEA
 * 虫情设备实时数据
 *
 * @author :shiminghao
 * @date :2023/5/5 14:14
 */
@Data
public class DeviceWormRealTimeVo {
    /**
     * 设备编号
     */
    private String deviceAddr;
    /**
     * 降雨状态
     */
    private String rain;
    /**
     * 杀虫挡板（0：关闭；1：打开）
     */
    private String wormFlap;
    /**
     * 杀虫仓温度
     */
    private String insecticideTem;
    /**
     * 震动装置（0：关闭；1：打开）
     */
    private String shake;
    /**
     * 经度
     */
    private String lng;
    /**
     * 烘干挡板（0：关闭；1：打开）
     */
    private String dryingFlap;
    /**
     * 杀虫控制（0：关闭；1：打开）
     */
    private String insecticide;
    /**
     * 移虫挡板（0：关闭；1：打开）
     */
    private String moveWorm;
    /**
     * 运行模式 1: 自动  0:  手动
     */
    private String mode;
    /**
     * 烘干控制（0：关闭；1：打开）
     */
    private String drying;
    /**
     * 虫雨挡板（0：关闭；1：打开）
     */
    private String rainFlap;
    /**
     * 诱虫灯状态（0：关闭；1：打开）
     */
    private String attractWorm;
    /**
     * 光照度
     */
    private String illum;
    /**
     * 烘干仓温度
     */
    private String dryingTem;
    /**
     * 纬度
     */
    private String lat;
    /**
     * 补光灯（0：关闭；1：打开）
     */
    private String fillLight;
    /**
     * 设备状态   online offline
     */
    private String status;
}
