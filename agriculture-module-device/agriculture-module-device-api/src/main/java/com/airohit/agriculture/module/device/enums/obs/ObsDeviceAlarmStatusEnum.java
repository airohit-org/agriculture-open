package com.airohit.agriculture.module.device.enums.obs;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @Description: 告警等级枚举
 * @Author: hanliyao
 * @Date: 2023/7/12 13:46
 */
@Getter
@AllArgsConstructor
public enum ObsDeviceAlarmStatusEnum {

    NORMAL(0, "正常"),
    UPPER(1, "超上限报警"),
    FLOOR(2, "超下限报警"),
    CLOSE(3, "开关闭合报警"),
    OFF(4, "开关断开报警"),
    REMOTE(5, "遥调报警");

    /**
     * 告警等级
     */
    private final Integer code;
    /**
     * 告警说明
     */
    private final String explain;

    public static ObsDeviceAlarmStatusEnum getByCode(Integer code) {
        return ArrayUtil.firstMatch(o -> Objects.equals(o.getCode(), code), values());
    }
}
