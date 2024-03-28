package com.airohit.agriculture.module.device.vo.farmyun.irrigation.two;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/6 14:23
 */
public class IrrigationFactorRegulatingResponseVo {


    /**
     * factorId : 88888888_3
     * regularValue : 0
     * regularText : 断开
     * alarmLevel : 0
     * createTime : null
     */

    private String factorId;
    private int regularValue;
    private String regularText;
    private int alarmLevel;

    public String getFactorId() {
        return factorId;
    }

    public void setFactorId(String factorId) {
        this.factorId = factorId;
    }

    public int getRegularValue() {
        return regularValue;
    }

    public void setRegularValue(int regularValue) {
        this.regularValue = regularValue;
    }

    public String getRegularText() {
        return regularText;
    }

    public void setRegularText(String regularText) {
        this.regularText = regularText;
    }

    public int getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(int alarmLevel) {
        this.alarmLevel = alarmLevel;
    }
}
