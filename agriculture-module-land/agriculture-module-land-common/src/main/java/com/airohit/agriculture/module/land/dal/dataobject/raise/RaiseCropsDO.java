package com.airohit.agriculture.module.land.dal.dataobject.raise;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 种植作物 DO
 *
 * @author shiminghao
 */
@TableName("raise_crops")
@KeySequence("raise_crops_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaiseCropsDO extends BaseDO {

    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 作物名称
     */
    private String cropsName;
    /**
     * 编码
     */
    private String code;
    /**
     * 作物图标
     */
    private String imageUrl;
    /**
     * 状态（0正常 1关闭）
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;

    /**
     * 数据code
     */
    private String dataCode;

    private Long farmTenantId;

}
