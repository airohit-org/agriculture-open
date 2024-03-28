package com.airohit.agriculture.module.device.vo.farmyun.spore;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 16:28
 */
public class SporeAutoModeResponseVO {


    /**
     * deviceAddr : 44444444
     * controlMode : null
     * beginTime : 09:00:00
     * runHour : 24,0
     * samplingTime : 0,30
     * workInterval : 1,59
     * singlePulse : 425
     * pulseUpperLimit : null
     * yAxisMotor : null
     */

    private String deviceAddr;
    private String beginTime;
    private String runHour;
    private String samplingTime;
    private String workInterval;
    private int singlePulse;
    private long pulseUpperLimit;

    public String getDeviceAddr() {
        return deviceAddr;
    }

    public void setDeviceAddr(String deviceAddr) {
        this.deviceAddr = deviceAddr;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getRunHour() {
        return runHour;
    }

    public void setRunHour(String runHour) {
        this.runHour = runHour;
    }

    public String getSamplingTime() {
        return samplingTime;
    }

    public void setSamplingTime(String samplingTime) {
        this.samplingTime = samplingTime;
    }

    public String getWorkInterval() {
        return workInterval;
    }

    public void setWorkInterval(String workInterval) {
        this.workInterval = workInterval;
    }

    public int getSinglePulse() {
        return singlePulse;
    }

    public void setSinglePulse(int singlePulse) {
        this.singlePulse = singlePulse;
    }

    public long getPulseUpperLimit() {
        return pulseUpperLimit;
    }

    public void setPulseUpperLimit(long pulseUpperLimit) {
        this.pulseUpperLimit = pulseUpperLimit;
    }
}
