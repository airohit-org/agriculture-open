package com.airohit.agriculture.module.device.vo.farmyun.irrigation.three;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/6 14:10
 */
@Data
public class UpdateDevInfoRequestVo {

    private String deviceAddr;
    private String deviceName;
    private double devicelng;
    private double devicelat;
    private int saveDateInterval;
    private int offlineInterval;
    private String alertDataStatus;
    private int phoneOfflineNotification;
    private int phoneAlarmInterval;
    private int phoneMaxSendingNumber;
    private int emailOfflineNotification;
    private int emailAlarmInterval;
    private int emailMaxSendingNumber;
}
