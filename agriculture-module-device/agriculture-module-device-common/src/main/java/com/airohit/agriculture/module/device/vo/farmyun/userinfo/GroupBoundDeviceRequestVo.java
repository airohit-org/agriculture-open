package com.airohit.agriculture.module.device.vo.farmyun.userinfo;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 13:44
 */
@Data
public class GroupBoundDeviceRequestVo {
    /**
     * 区域ID
     */
    private String groupId;
    /**
     * 设备地址（多个用英文逗号分隔）
     */
    private String deviceAddrs;
}
