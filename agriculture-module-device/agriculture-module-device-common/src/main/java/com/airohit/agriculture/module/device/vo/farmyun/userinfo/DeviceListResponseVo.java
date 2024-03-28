package com.airohit.agriculture.module.device.vo.farmyun.userinfo;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 13:27
 */
@Data
public class DeviceListResponseVo {

    /**
     * deviceAddr : 88888888
     * groupId : 2F79166C3AC1FCCBBB72B024446C5976
     * deviceType : irrigation
     * deviceEnabled : 1
     * deviceName : 88888888
     * devicelng : 115.95706
     * devicelat : 3.062559
     * deviceIccId : null
     * correlationDeviceAddr : null
     * correlationTime : null
     * saveDateInterval : null
     * offlineInterval : null
     * httpUrl : null
     * rtmpUrl : null
     * city : null
     * createTime : null
     * iccIdData : null
     */
    /**
     * 设备地址
     */
    private String deviceAddr;
    /**
     * 区域ID
     */
    private String groupId;
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 经度
     */
    private double devicelng;
    /**
     * 纬度
     */
    private double devicelat;
    /**
     * 设备iccid
     */
    private Object deviceIccId;


}
