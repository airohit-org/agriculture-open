package com.airohit.agriculture.module.device.vo.farmyun.worm;

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
     * attractWorm 诱虫灯;
     * rainFlap 虫雨挡板;
     * wormFlap 杀虫挡板;
     * dryingFlap 烘干挡板;
     * moveWorm 移虫装置;
     * shake 震动装置;
     * fillLight 补光灯;
     * insecticide 杀虫控制;
     * drying 烘干控制;
     * camera 拍照;
     */
    private String module;
    /**
     * 0关闭 1开启
     */
    private String opt;
}
