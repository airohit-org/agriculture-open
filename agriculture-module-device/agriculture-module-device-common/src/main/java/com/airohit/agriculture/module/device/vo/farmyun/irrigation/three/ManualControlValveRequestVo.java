package com.airohit.agriculture.module.device.vo.farmyun.irrigation.three;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/6 14:39
 */
public class ManualControlValveRequestVo {

    private String deviceAddr;
    /**
     * 节点id
     */
    private String factorId;
    /**
     * 模式，1为手动，2为自动
     */
    private String mode;
}
