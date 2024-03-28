package com.airohit.agriculture.module.device.enums.obs;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @Description: 设备节点状态枚举
 * @Author: hanliyao
 * @Date: 2023/7/12 13:46
 */
@Getter
@AllArgsConstructor
public enum ObsDeviceNodeStautsEnum {

    OPEN("0", "关闭"),
    CLOSE("1", "打开");

    private final String code;
    /**
     * 说明
     */
    private final String explain;

    public static ObsDeviceNodeStautsEnum getByCode(String code) {
        return ArrayUtil.firstMatch(o -> Objects.equals(o.getCode(), code), values());
    }
}
