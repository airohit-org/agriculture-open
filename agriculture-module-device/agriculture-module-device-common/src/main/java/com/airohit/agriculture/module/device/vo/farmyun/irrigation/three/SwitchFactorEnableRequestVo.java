package com.airohit.agriculture.module.device.vo.farmyun.irrigation.three;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/6 14:21
 */
@Data
public class SwitchFactorEnableRequestVo {
    /**
     * 设备地址
     */
    private String devAddr;
    /**
     * 节点id（多个用英文逗号分隔）
     */
    private String factorIds;
    /**
     * 节点使能，0,关闭；1,打开
     */
    private String enable;
}
