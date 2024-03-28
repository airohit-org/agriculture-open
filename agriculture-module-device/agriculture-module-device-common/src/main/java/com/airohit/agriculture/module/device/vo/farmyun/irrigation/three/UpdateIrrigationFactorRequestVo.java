package com.airohit.agriculture.module.device.vo.farmyun.irrigation.three;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/6 14:16
 */
@Data
public class UpdateIrrigationFactorRequestVo {
    private String factorId;
    private int nodeType;
    private String factorName;
    private String enabled;
    private String unit;
    private int digits;
    private double coefficient;
    private int offset;
    private int upperLimit;
    private int lowerLimit;
    private int electricLowerlimit;
    private String smsEnabled;
    private String emailEnabled;
    private String offlineAlarmingSwitch;
    private String electricLimitSwitch;
    private String excessAlarmingSwitch;
}
