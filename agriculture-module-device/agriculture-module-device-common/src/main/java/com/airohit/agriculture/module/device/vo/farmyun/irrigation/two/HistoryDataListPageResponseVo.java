package com.airohit.agriculture.module.device.vo.farmyun.irrigation.two;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/6 15:05
 */
public class HistoryDataListPageResponseVo {


    /**
     * historyId : 1345
     * nodeId : 7
     * deviceAddress : 66668888
     * temStr : 0.0
     * humStr : 0.0
     * temValue : 0
     * humValue : 0
     * recordTime : 1660547896000
     * alarmStatus : 0
     */

    private int historyId;
    private int nodeId;
    private String deviceAddress;
    private String temStr;
    private String humStr;
    private double temValue;
    private double humValue;
    private long recordTime;
    private int alarmStatus;

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

    public long getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(long recordTime) {
        this.recordTime = recordTime;
    }

    public int getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(int alarmStatus) {
        this.alarmStatus = alarmStatus;
    }
}
