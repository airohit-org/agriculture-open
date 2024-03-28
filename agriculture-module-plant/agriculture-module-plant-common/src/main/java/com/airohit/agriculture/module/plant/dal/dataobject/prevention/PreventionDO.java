package com.airohit.agriculture.module.plant.dal.dataobject.prevention;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 防治方案 DO
 *
 * @author 管理员
 */
@TableName("diseases_prevention")
@KeySequence("diseases_prevention_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreventionDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Integer id;
    /**
     * 病虫害名称
     */
    private String diseasesName;
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
    /**
     * 农场编号
     */
    private Long farmTenantId;
    /**
     * 防治措施
     */
    private String measure;
    /**
     * 防治方案
     */
    private String preventionPlan;

}
