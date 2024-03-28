package com.airohit.agriculture.module.plant.dal.dataobject.plantypedata;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * 种植计划类型 DO
 *
 * @author 管理员
 */
@TableName("planting_plan_type_data")
@KeySequence("planting_plan_type_data_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanTypeDataDO extends BaseDO {

    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 阶段名称
     */
    private String stageName;
    /**
     * 阶段编码
     */
    private String stageCode;
    /**
     * 计划编号
     */
    private Integer plantingPlanId;
    /**
     * 周期（天）
     */
    private Integer period;

    /**
     * 周期名称
     */
    private String periodName;

    /**
     * 计划日期
     */
    private Date plantingPlanDate;
    /**
     * 备注
     */
    private String remark;

    /**
     * 父编号
     */
    private Integer parentId;

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
