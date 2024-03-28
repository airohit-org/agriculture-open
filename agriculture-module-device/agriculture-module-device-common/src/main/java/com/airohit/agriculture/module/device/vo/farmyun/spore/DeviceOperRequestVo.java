package com.airohit.agriculture.module.device.vo.farmyun.spore;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 16:34
 */
@Data
public class DeviceOperRequestVo {
    /**
     * 设备地址
     */
    private String deviceAddr;
    /**
     * IamphouseStatus 光源；
     * AdmissionPortStatus 吸风风扇；
     * BlowVentStatus 排风风扇；
     * ZBDMotorStatus 载波带电机运行；
     * SamplingPortStatus 采样电机运行；
     * photograph 拍照
     */
    private String module;
    /**
     * 0关闭 1开启
     */
    private String opt;
}
