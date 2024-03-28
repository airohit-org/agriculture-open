package com.airohit.agriculture.module.plant.dal.dataobject.taskraking;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 整地 DO
 *
 * @author 史铭浩
 */
@TableName("agro_task_raking")
@KeySequence("agro_task_raking_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRakingDO extends BaseDO {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 任务基本信息id
     */
    private Integer agroTaskId;
    /**
     * 深翻深度
     */
    private String turningOverDepth;
    /**
     * 耙地深度
     */
    private String rakingDepth;
    /**
     * 耙地次数
     */
    private Integer rakingTimes;
    /**
     * 旋地深度
     */
    private String gyrationDepth;
    /**
     * 旋转次数
     */
    private Integer gyrationTimes;
    /**
     * 农机
     */
    private String farmMachinery;
    /**
     * 备注
     */
    private String remark;

}
