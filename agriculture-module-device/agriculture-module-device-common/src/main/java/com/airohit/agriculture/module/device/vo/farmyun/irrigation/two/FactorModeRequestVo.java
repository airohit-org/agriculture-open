package com.airohit.agriculture.module.device.vo.farmyun.irrigation.two;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 16:32
 */
@Data
public class FactorModeRequestVo {
    /**
     * 节点id
     */
    private String factorId;
    /**
     * 模式，1为手动，2为自动
     */
    private String mode;
}
