package com.airohit.agriculture.module.plant.vo.taskInfo;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/17 13:37
 */
@Data
public class TaskLandPlanVo {
    private String landName;
    private String planName;
    private String planningStage;
    private String parentStageName;
    private Integer planningStageId;
    private Integer parentStageNameId;

}
