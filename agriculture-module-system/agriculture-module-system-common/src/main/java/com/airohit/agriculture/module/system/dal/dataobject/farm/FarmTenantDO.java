package com.airohit.agriculture.module.system.dal.dataobject.farm;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/10 15:50
 */
@TableName("farm_tenant")
@KeySequence("farm_tenant_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FarmTenantDO extends BaseDO {
    /**
     * Id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * Id
     */
    private Integer farmId;
    /**
     * Id
     */
    private Long tenantId;
}
