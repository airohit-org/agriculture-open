package com.airohit.agriculture.module.plant.enums.taskInfo;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/6 13:14
 */
@AllArgsConstructor
@Getter
public enum TaskStatusEnum {
    NOT_APPOINT("未指派", 1),
    NOT_STARTED("未开始", 2),
    IN_PROGRESS("进行中", 3),
    INCOMPLETE("未完成", 4),
    COMPLETED("已完成", 5),
    OVERDUE("逾期", 6);

    private final String name;
    private final Integer status;

    public static TaskStatusEnum getByStatus(Integer status) {
        return ArrayUtil.firstMatch(o -> o.getStatus().equals(status), values());
    }
}
