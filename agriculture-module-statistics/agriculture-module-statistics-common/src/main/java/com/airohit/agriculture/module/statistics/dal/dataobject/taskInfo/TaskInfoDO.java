package com.airohit.agriculture.module.statistics.dal.dataobject.taskInfo;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 农事任务基本信息 DO
 *
 * @author 史铭浩
 */
@TableName("agro_task_info")
@KeySequence("agro_task_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskInfoDO extends BaseDO {

    /**
     * Id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
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
     * 任务对应的表名
     */
    private String typeTableName;
    /**
     * 任务对应的模型名称
     */
    private String typeModelName;
    /**
     * 计划阶段
     */
    private String planningStage;
    /**
     * 种植计划模版ID
     */
    private Integer plantingPlanId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 农户ID
     */
    private Integer peasantId;
}
