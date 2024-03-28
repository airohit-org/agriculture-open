package com.airohit.agriculture.module.system.dal.dataobject.dept;

import com.airohit.agriculture.framework.common.enums.CommonStatusEnum;
import com.airohit.agriculture.framework.tenant.core.db.TenantBaseDO;
import com.airohit.agriculture.module.system.dal.dataobject.user.AdminUserDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门表
 *
 * @author shiminghao
 * @author shiminghao
 */
@TableName("system_dept")
@KeySequence("system_dept_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptDO extends TenantBaseDO {

    /**
     * 部门ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 父部门ID
     * <p>
     * 关联 {@link #id}
     */
    private Long parentId;
    /**
     * 显示顺序
     */
    private Integer sort;
    /**
     * 负责人
     * <p>
     * 关联 {@link AdminUserDO#getId()}
     */
    private Long leaderUserId;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 部门状态
     * <p>
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;

}
