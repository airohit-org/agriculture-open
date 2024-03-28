package com.airohit.agriculture.module.system.dal.dataobject.tenant;

import com.airohit.agriculture.framework.common.enums.CommonStatusEnum;
import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.airohit.agriculture.framework.mybatis.core.type.JsonLongSetTypeHandler;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.util.Set;

/**
 * 租户套餐 DO
 *
 * @author shiminghao
 */
@TableName(value = "system_tenant_package", autoResultMap = true)
@KeySequence("system_tenant_package_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantPackageDO extends BaseDO {

    /**
     * 套餐编号，自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 套餐名，唯一
     */
    private String name;
    /**
     * 租户状态
     * <p>
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 关联的菜单编号
     */
    @TableField(typeHandler = JsonLongSetTypeHandler.class)
    private Set<Long> menuIds;

}
