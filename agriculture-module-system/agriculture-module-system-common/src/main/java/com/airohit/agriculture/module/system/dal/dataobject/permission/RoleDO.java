package com.airohit.agriculture.module.system.dal.dataobject.permission;

import com.airohit.agriculture.framework.common.enums.CommonStatusEnum;
import com.airohit.agriculture.framework.mybatis.core.type.JsonLongSetTypeHandler;
import com.airohit.agriculture.framework.tenant.core.db.TenantBaseDO;
import com.airohit.agriculture.module.system.enums.permission.DataScopeEnum;
import com.airohit.agriculture.module.system.enums.permission.RoleTypeEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * 角色 DO
 *
 * @author shiminghao
 */
@TableName(value = "system_role", autoResultMap = true)
@KeySequence("system_role_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDO extends TenantBaseDO {

    /**
     * 角色ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色标识
     * <p>
     * 枚举
     */
    private String code;
    /**
     * 角色排序
     */
    private Integer sort;
    /**
     * 角色状态
     * <p>
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 角色类型
     * <p>
     * 枚举 {@link RoleTypeEnum}
     */
    private Integer type;
    /**
     * 备注
     */
    private String remark;

    /**
     * 数据范围
     * <p>
     * 枚举 {@link DataScopeEnum}
     */
    private Integer dataScope;
    /**
     * 数据范围(指定部门数组)
     * <p>
     * 适用于 {@link #dataScope} 的值为 {@link DataScopeEnum#DEPT_CUSTOM} 时
     */
    @TableField(typeHandler = JsonLongSetTypeHandler.class)
    private Set<Long> dataScopeDeptIds;

}
