package com.airohit.agriculture.module.device.vo.farmyun.irrigation.two;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/6 14:21
 */
@Data
public class BatchNodeEnableRequestVo {
    /**
     * 设备地址
     */
    private String devAddr;
    /**
     * 1采集器 2阀门
     */
    private String factorType;
    /**
     * 节点使能，0,关闭；1,打开
     */
    private String enable;
}
