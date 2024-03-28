package com.airohit.agriculture.module.plant.vo.taskInfo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/6/9 11:01
 */
@Data
public class TaskMessageVo {
    /**
     * 农户ID
     */
    private Integer peasantId;
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 任务名称
     */
    private String agroName;
    /**
     * 开始日期
     */
    private LocalDateTime startDate;
    /**
     * 任务周期
     */
    private Integer taskPeriod;
    /**
     * 任务类型
     */
    private Integer type;
    /**
     * 地块区域名称
     */
    private String name;
    /**
     * 作物名称
     */
    private String cropsName;
}
