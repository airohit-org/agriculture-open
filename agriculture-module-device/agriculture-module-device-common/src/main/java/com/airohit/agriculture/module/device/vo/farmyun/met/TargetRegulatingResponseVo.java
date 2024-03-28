package com.airohit.agriculture.module.device.vo.farmyun.met;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 15:17
 */
public class TargetRegulatingResponseVo {


    /**
     * regularId : 4
     * nodeId : 5
     * deviceAddr : 10000000
     * regularValue : 3.0
     * regularText : 东南风
     * alarmEnable : 0
     */

    private String regularId;
    private int nodeId;
    private String deviceAddr;
    private double regularValue;
    private String regularText;
    private int alarmEnable;

    public String getRegularId() {
        return regularId;
    }

    public void setRegularId(String regularId) {
        this.regularId = regularId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getDeviceAddr() {
        return deviceAddr;
    }

    public void setDeviceAddr(String deviceAddr) {
        this.deviceAddr = deviceAddr;
    }

    public double getRegularValue() {
        return regularValue;
    }

    public void setRegularValue(double regularValue) {
        this.regularValue = regularValue;
    }

    public String getRegularText() {
        return regularText;
    }

    public void setRegularText(String regularText) {
        this.regularText = regularText;
    }

    public int getAlarmEnable() {
        return alarmEnable;
    }

    public void setAlarmEnable(int alarmEnable) {
        this.alarmEnable = alarmEnable;
    }
}
