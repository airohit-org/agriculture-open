package com.airohit.agriculture.module.device.vo.farmyun.met;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 15:22
 */
@Data
public class UpdateDeviceRequestVo {
    /**
     * 设备地址
     */
    private String deviceAddr;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 纬度
     */
    private double lat;
    /**
     * 经度
     */
    private double lng;
    /**
     * 离线判断间隔
     */
    private int offlineInterval;
    /**
     * 数据保存间隔
     */
    private int saveDataInterval;

}
