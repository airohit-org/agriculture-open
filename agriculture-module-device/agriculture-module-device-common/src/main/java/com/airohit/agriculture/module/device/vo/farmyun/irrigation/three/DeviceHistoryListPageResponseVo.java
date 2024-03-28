package com.airohit.agriculture.module.device.vo.farmyun.irrigation.three;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/6 14:34
 */
public class DeviceHistoryListPageResponseVo {


    /**
     * id : 75097000
     * deviceAddr : 40165167
     * factorId : 40165167_1
     * factorName : 空气温度
     * createTime : 1660547896000
     * valueText : 0.0
     * electricQuantity : 100
     * signal : 200
     * alarming : 0
     * value : 0.0
     */

    private int id;
    private String deviceAddr;
    private String factorId;
    private String factorName;
    private long createTime;
    private String valueText;
    private float electricQuantity;
    private float signal;
    private String alarming;
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceAddr() {
        return deviceAddr;
    }

    public void setDeviceAddr(String deviceAddr) {
        this.deviceAddr = deviceAddr;
    }

    public String getFactorId() {
        return factorId;
    }

    public void setFactorId(String factorId) {
        this.factorId = factorId;
    }

    public String getFactorName() {
        return factorName;
    }

    public void setFactorName(String factorName) {
        this.factorName = factorName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    public float getElectricQuantity() {
        return electricQuantity;
    }

    public void setElectricQuantity(float electricQuantity) {
        this.electricQuantity = electricQuantity;
    }

    public float getSignal() {
        return signal;
    }

    public void setSignal(float signal) {
        this.signal = signal;
    }

    public String getAlarming() {
        return alarming;
    }

    public void setAlarming(String alarming) {
        this.alarming = alarming;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
