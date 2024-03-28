package com.airohit.agriculture.module.plant.enums.taskInfo;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/3 20:31
 */
@AllArgsConstructor
@Getter
public enum TaskInfoEnum {
    TASK_FERTILIZER(0, "agro_task_fertilizer", "施肥", "com.airohit.agriculture.module.plant.vo.taskInfo.TaskFertilizerBaseVO"),
    TASK_INTERTILL(1, "agro_task_intertill", "中耕", "com.airohit.agriculture.module.plant.vo.taskInfo.TaskIntertillBaseVO"),
    TASK_IRRIGATION(2, "agro_task_irrigation", "灌溉", "com.airohit.agriculture.module.plant.vo.taskInfo.TaskIrrigationBaseVO"),
    TASK_PESTICIDE(3, "agro_task_pesticide", "打药", "com.airohit.agriculture.module.plant.vo.taskInfo.TaskPesticideBaseVO"),
    TASK_RAKING(4, "agro_task_raking", "整地", "com.airohit.agriculture.module.plant.vo.taskInfo.TaskRakingBaseVO"),
    TASK_REAP(5, "agro_task_reap", "收割", "com.airohit.agriculture.module.plant.vo.taskInfo.TaskReapBaseVO"),
    TASK_SEEDING(6, "agro_task_seeding", "播种", "com.airohit.agriculture.module.plant.vo.taskInfo.TaskSeedingBaseVO"),
    TASK_WEED(7, "agro_task_weed", "除草", "com.airohit.agriculture.module.plant.vo.taskInfo.TaskWeedBaseVO"),
    ;
    /**
     * 编码
     */
    private final Integer type;
    /**
     * 编码
     */
    private final String typeTableName;
    /**
     * 名字
     */
    private final String name;
    /**
     * 模型名
     */
    private final String typeModelName;


    public static TaskInfoEnum getByType(Integer type) {
        return ArrayUtil.firstMatch(o -> o.getType().equals(type), values());
    }

    public static List<HashMap<String, String>> getEnumList() {
        List<HashMap<String, String>> list = new ArrayList<>();
        for (TaskInfoEnum taskInfoEnum : EnumSet.allOf(TaskInfoEnum.class)) {
            HashMap<String, String> map = new HashMap<>();
            map.put("type", taskInfoEnum.type.toString());
            map.put("typeTableName", taskInfoEnum.typeTableName);
            map.put("name", taskInfoEnum.name);
            map.put("typeModelName", taskInfoEnum.typeModelName);
            list.add(map);
        }
        return list;
    }
}
