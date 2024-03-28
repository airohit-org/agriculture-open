package com.airohit.agriculture.module.plant.dal.dataobject.plan;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * 种植计划 DO
 *
 * @author 管理员
 */
@TableName("planting_plan")
@KeySequence("planting_plan_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanDO extends BaseDO {

    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 地块编号
     */
    private Integer landId;
    /**
     * 计划名称
     */
    private String planName;
    /**
     * 种植作物
     */
    private String crops;
    /**
     * 作物品种
     */
    private String cropsType;
    /**
     * 开始日期
     */
    private Date startTime;
    /**
     * 结束日期
     */
    private Date endTime;
    /**
     * 1 管理员 2 用户
     */
    private Integer type;
    /**
     * 状态（0已发布 1未发布）
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 种植计划模版编号
     */
    private Integer plantingPlanTemplateId;
    /**
     * 是否克隆 1是 0 否
     */
    private Boolean isClone;
    /**
     * 计划周期
     */
    private Integer planningCycle;
    /**
     * 是否已绑定地块 1 是 0 否
     */
    private Integer isBindLand;

    /**
     * 是否是模版 1 是 0 否
     */
    private Integer isTemplate;

    /**
     * 租户编号
     */
    private Long tenantId;

    /**
     * 农场编号
     */
    private long farmTenantId;

    /**
     * 数据code
     */
    private String dataCode;

}
