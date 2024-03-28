package com.airohit.agriculture.module.device.vo.farmyun.spore;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 16:24
 */
public class BtchDeviceResponseVo {


    /**
     * deviceAddr : 0415220001
     * deviceType : spore
     * deviceName : 0415220001
     * lng : 22.6700690292
     * lat : 25.0959518239
     * saveDateInterval : 30
     * offlineInterval : 5
     * deviceEnabled : 1
     * city : null
     * createTime : 2022-06-20 13:49:47
     */

    private String deviceAddr;
    private String deviceName;
    private double lng;
    private double lat;
    private int saveDateInterval;
    private int offlineInterval;
    private String createTime;

    public String getDeviceAddr() {
        return deviceAddr;
    }

    public void setDeviceAddr(String deviceAddr) {
        this.deviceAddr = deviceAddr;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getSaveDateInterval() {
        return saveDateInterval;
    }

    public void setSaveDateInterval(int saveDateInterval) {
        this.saveDateInterval = saveDateInterval;
    }

    public int getOfflineInterval() {
        return offlineInterval;
    }

    public void setOfflineInterval(int offlineInterval) {
        this.offlineInterval = offlineInterval;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
