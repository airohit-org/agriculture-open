package com.airohit.agriculture.module.device.vo.farmyun.soil;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 15:57
 */
@Data
public class HistoryDataPageResponseVo {

    /**
     * historyId : 14866433
     * nodeId : 20
     * deviceAddress : 10000000
     * temStr : 4823555.50
     * humStr : 368.00
     * temValue : 4823555.5
     * humValue : 368
     * alarmStatus : 1
     * recordTime : 1655977112248
     */

    private int historyId;
    private int nodeId;
    private String deviceAddress;
    private String temStr;
    private String humStr;
    private double temValue;
    private double humValue;
    private int alarmStatus;
    private long recordTime;

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public String getTemStr() {
        return temStr;
    }

    public void setTemStr(String temStr) {
        this.temStr = temStr;
    }

    public String getHumStr() {
        return humStr;
    }

    public void setHumStr(String humStr) {
        this.humStr = humStr;
    }

    public double getTemValue() {
        return temValue;
    }

    public void setTemValue(double temValue) {
        this.temValue = temValue;
    }

    public double getHumValue() {
        return humValue;
    }

    public void setHumValue(double humValue) {
        this.humValue = humValue;
    }

    public int getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(int alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public long getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(long recordTime) {
        this.recordTime = recordTime;
    }
}
