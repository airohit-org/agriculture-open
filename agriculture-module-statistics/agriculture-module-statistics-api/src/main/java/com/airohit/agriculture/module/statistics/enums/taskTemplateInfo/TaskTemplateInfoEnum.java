package com.airohit.agriculture.module.statistics.enums.taskTemplateInfo;

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
public enum TaskTemplateInfoEnum {
    TASK_TEMPLATE_FERTILIZER(0, "agro_task_template_fertilizer", "施肥", "com.airohit.agriculture.module.plant.vo.taskTemplateInfo.TaskTemplateFertilizerBaseVO"),
    TASK_TEMPLATE_INTERTILL(1, "agro_task_template_intertill", "中耕", "com.airohit.agriculture.module.plant.vo.taskTemplateInfo.TaskTemplateIntertillBaseVO"),
    TASK_TEMPLATE_IRRIGATION(2, "agro_task_template_irrigation", "灌溉", "com.airohit.agriculture.module.plant.vo.taskTemplateInfo.TaskTemplateIrrigationBaseVO"),
    TASK_TEMPLATE_PESTICIDE(3, "agro_task_template_pesticide", "打药", "com.airohit.agriculture.module.plant.vo.taskTemplateInfo.TaskTemplatePesticideBaseVO"),
    TASK_TEMPLATE_RAKING(4, "agro_task_template_raking", "整地", "com.airohit.agriculture.module.plant.vo.taskTemplateInfo.TaskTemplateRakingBaseVO"),
    TASK_TEMPLATE_REAP(5, "agro_task_template_reap", "收割", "com.airohit.agriculture.module.plant.vo.taskTemplateInfo.TaskTemplateReapBaseVO"),
    TASK_TEMPLATE_SEEDING(6, "agro_task_template_seeding", "播种", "com.airohit.agriculture.module.plant.vo.taskTemplateInfo.TaskTemplateSeedingBaseVO"),
    TASK_TEMPLATE_WEED(7, "agro_task_template_weed", "除草", "com.airohit.agriculture.module.plant.vo.taskTemplateInfo.TaskTemplateWeedBaseVO"),
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
     * 名字
     */
    private final String typeModelName;

    public static TaskTemplateInfoEnum getByType(Integer type) {
        return ArrayUtil.firstMatch(o -> o.getType().equals(type), values());
    }

    public static List<HashMap<String, String>> getEnumList() {
        List<HashMap<String, String>> list = new ArrayList<>();
        for (TaskTemplateInfoEnum taskInfoEnum : EnumSet.allOf(TaskTemplateInfoEnum.class)) {
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
