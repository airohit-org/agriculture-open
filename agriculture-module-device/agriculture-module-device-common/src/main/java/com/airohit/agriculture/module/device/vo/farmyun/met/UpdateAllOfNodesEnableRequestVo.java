package com.airohit.agriculture.module.device.vo.farmyun.met;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 15:20
 */
@Data
public class UpdateAllOfNodesEnableRequestVo {
    /**
     * 设备地址
     */
    private String deviceAddr;
    /**
     * 0:关闭 1:开启
     */
    private String enable;
}
