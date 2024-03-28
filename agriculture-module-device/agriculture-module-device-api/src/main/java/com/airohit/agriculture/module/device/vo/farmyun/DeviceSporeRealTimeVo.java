package com.airohit.agriculture.module.device.vo.farmyun;

import lombok.Data;

/**
 * Created with IDEA
 * 孢子设备实时数据
 *
 * @author :shiminghao
 * @date :2023/5/5 14:06
 */
@Data
public class DeviceSporeRealTimeVo {
    /**
     * 控制模式(0:手动；1：自动）
     */
    private String controlMode;
    /**
     * 载玻带单次运动距离的脉冲数
     */
    private String ZBDMotorPulse;
    /**
     * 排气口状态（0：关闭；1：打开）
     */
    private String BlowVentStatus;
    /**
     * 光源状态(0:关闭；1：开启)
     */
    private String IamphouseStatus;
    /**
     * 纬度
     */
    private String Latitude;
    /**
     * 采样口状态（0：关闭；1：打开）
     */
    private String SamplingPortStatus;
    /**
     * Y轴电机状态（0：停止；1：运行）
     */
    private String YMotorStatus;
    /**
     * 经度
     */
    private String Longitude;
    /**
     * 吸气口状态（0：关闭；1：打开）
     */
    private String AdmissionPortStatus;
    /**
     * Y轴电机脉冲范围（最小值，最大值）
     */
    private String motorPulse;
    /**
     * 采集的累计脉冲数
     */
    private String PulseTotal;
    /**
     * Y轴电机脉冲数（0-55000）
     */
    private String YMotorPulse;
    /**
     * 载玻带电机状态（0：停止；1：运行）
     */
    private String ZBDMotorStatus;
    /**
     * 采集的单次脉冲数
     */
    private String PulseCurrent;
    /**
     * Y轴电机方向（0：前进；1：后退）
     */
    private String YMotorDirection;
    /**
     * 海拔高度
     */
    private String Altitude;
}
