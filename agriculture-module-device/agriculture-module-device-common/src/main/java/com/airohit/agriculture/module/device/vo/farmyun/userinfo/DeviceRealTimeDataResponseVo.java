package com.airohit.agriculture.module.device.vo.farmyun.userinfo;

import com.airohit.agriculture.module.device.enums.obs.ObsDeviceTypeEnum;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 13:36
 */
@Data
public class DeviceRealTimeDataResponseVo {


    /**
     * deviceAddr : 10000000
     * deviceName : 气象10000000
     * lat : 40.0121708354832
     * lng : 11.097684058355
     * status : online
     * deviceType : met
     * timeStamp : 0
     * data : [{"nodeName":"降雨量","temAlarmStatus":0,"humValueStr":"0.0","nodeType":2,"priority":0,"humValue":0,"temValue":0,"humUnit":"ug/m3","temUnit":"mm","temName":"降雨量","temValueStr":"0.0","humAlarmStatus":0,"nodeId":1,"humName":"PM2.5"}]
     */
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
     *
     */
    private double lng;
    /**
     * 状态 online,offline,alarm
     */
    private String status;
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 设备实时数据,参考如下枚举
     *
     * @see ObsDeviceTypeEnum
     */
    private List<JSONObject> data;

}
