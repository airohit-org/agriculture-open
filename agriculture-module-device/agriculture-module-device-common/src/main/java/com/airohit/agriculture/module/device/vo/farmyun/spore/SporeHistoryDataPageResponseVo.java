package com.airohit.agriculture.module.device.vo.farmyun.spore;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 16:40
 */
public class SporeHistoryDataPageResponseVo {


    /**
     * recordId : 956946
     * deviceAddr : 44444444
     * value : 设备地址码:44444444|设备门状态:关闭|单次移动脉冲采样:446|累计脉冲采样:51840|运行模式:自动模式|光源状态:关闭|吸气口状态:关闭|排气口状态:关闭|采样口状态:关闭|载玻带电机状态:关闭|Y轴电机状态:0
     * createTime : 2022-08-02 18:58:47
     */

    private int recordId;
    private String deviceAddr;
    private String value;
    private String createTime;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getDeviceAddr() {
        return deviceAddr;
    }

    public void setDeviceAddr(String deviceAddr) {
        this.deviceAddr = deviceAddr;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
