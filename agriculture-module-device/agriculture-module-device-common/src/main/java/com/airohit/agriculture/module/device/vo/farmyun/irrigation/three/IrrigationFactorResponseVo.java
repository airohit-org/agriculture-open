package com.airohit.agriculture.module.device.vo.farmyun.irrigation.three;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/6 14:12
 */
public class IrrigationFactorResponseVo {


    /**
     * factorId : 40165167_3
     * factorNo : 3
     * factorType : 1
     * deviceAddr : 40165167
     * nodeType : 1
     * factorName : 土壤温度
     * enabled : 1
     * unit : ℃
     * digits : 1
     * coefficient : 0.1
     * offset : 0
     * upperLimit : 100
     * lowerLimit : 0
     * electricLowerlimit : 5
     * smsEnabled : 0
     * emailEnabled : 0
     * offlineAlarmingSwitch : 0
     * offlineAlarmingAlarmcontent : null
     * electricLimitSwitch : 0
     * electricLimitAlarmcontent : null
     * excessAlarmingSwitch : 0
     * excessAlarmingAlarmcontent : null
     * createTime : 1660547896000
     */

    private String factorId;
    private int factorNo;
    private String factorType;
    private String deviceAddr;
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
    private String offlineAlarmingAlarmcontent;
    private String electricLimitSwitch;
    private String electricLimitAlarmcontent;
    private String excessAlarmingSwitch;
    private String excessAlarmingAlarmcontent;
    private long createTime;

    public String getFactorId() {
        return factorId;
    }

    public void setFactorId(String factorId) {
        this.factorId = factorId;
    }

    public int getFactorNo() {
        return factorNo;
    }

    public void setFactorNo(int factorNo) {
        this.factorNo = factorNo;
    }

    public String getFactorType() {
        return factorType;
    }

    public void setFactorType(String factorType) {
        this.factorType = factorType;
    }

    public String getDeviceAddr() {
        return deviceAddr;
    }

    public void setDeviceAddr(String deviceAddr) {
        this.deviceAddr = deviceAddr;
    }

    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public String getFactorName() {
        return factorName;
    }

    public void setFactorName(String factorName) {
        this.factorName = factorName;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getDigits() {
        return digits;
    }

    public void setDigits(int digits) {
        this.digits = digits;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(int lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public int getElectricLowerlimit() {
        return electricLowerlimit;
    }

    public void setElectricLowerlimit(int electricLowerlimit) {
        this.electricLowerlimit = electricLowerlimit;
    }

    public String getSmsEnabled() {
        return smsEnabled;
    }

    public void setSmsEnabled(String smsEnabled) {
        this.smsEnabled = smsEnabled;
    }

    public String getEmailEnabled() {
        return emailEnabled;
    }

    public void setEmailEnabled(String emailEnabled) {
        this.emailEnabled = emailEnabled;
    }

    public String getOfflineAlarmingSwitch() {
        return offlineAlarmingSwitch;
    }

    public void setOfflineAlarmingSwitch(String offlineAlarmingSwitch) {
        this.offlineAlarmingSwitch = offlineAlarmingSwitch;
    }

    public String getOfflineAlarmingAlarmcontent() {
        return offlineAlarmingAlarmcontent;
    }

    public void setOfflineAlarmingAlarmcontent(String offlineAlarmingAlarmcontent) {
        this.offlineAlarmingAlarmcontent = offlineAlarmingAlarmcontent;
    }

    public String getElectricLimitSwitch() {
        return electricLimitSwitch;
    }

    public void setElectricLimitSwitch(String electricLimitSwitch) {
        this.electricLimitSwitch = electricLimitSwitch;
    }

    public String getElectricLimitAlarmcontent() {
        return electricLimitAlarmcontent;
    }

    public void setElectricLimitAlarmcontent(String electricLimitAlarmcontent) {
        this.electricLimitAlarmcontent = electricLimitAlarmcontent;
    }

    public String getExcessAlarmingSwitch() {
        return excessAlarmingSwitch;
    }

    public void setExcessAlarmingSwitch(String excessAlarmingSwitch) {
        this.excessAlarmingSwitch = excessAlarmingSwitch;
    }

    public String getExcessAlarmingAlarmcontent() {
        return excessAlarmingAlarmcontent;
    }

    public void setExcessAlarmingAlarmcontent(String excessAlarmingAlarmcontent) {
        this.excessAlarmingAlarmcontent = excessAlarmingAlarmcontent;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
