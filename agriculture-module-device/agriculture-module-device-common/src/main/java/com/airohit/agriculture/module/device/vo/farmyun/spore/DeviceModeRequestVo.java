package com.airohit.agriculture.module.device.vo.farmyun.spore;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 16:32
 */
@Data
public class DeviceModeRequestVo {
    /**
     * 设备地址
     */
    private String deviceAddr;
    /**
     * 0手动 1自动
     */
    private String mode;
}
