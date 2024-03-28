package com.airohit.agriculture.module.infra.dal.dataobject.codegen;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.airohit.agriculture.module.infra.dal.dataobject.db.DataSourceConfigDO;
import com.airohit.agriculture.module.infra.enums.codegen.CodegenSceneEnum;
import com.airohit.agriculture.module.infra.enums.codegen.CodegenTemplateTypeEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 代码生成 table 表定义
 *
 * @author shiminghao
 */
@TableName(value = "infra_codegen_table", autoResultMap = true)
@KeySequence("infra_codegen_table_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CodegenTableDO extends BaseDO {

    /**
     * ID 编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 数据源编号
     * <p>
     * 关联 {@link DataSourceConfigDO#getId()}
     */
    private Long dataSourceConfigId;
    /**
     * 生成场景
     * <p>
     * 枚举 {@link CodegenSceneEnum}
     */
    private Integer scene;

    // ========== 表相关字段 ==========

    /**
     * 表名称
     */
    private String tableName;
    /**
     * 表描述
     */
    private String tableComment;
    /**
     * 备注
     */
    private String remark;

    // ========== 类相关字段 ==========

    /**
     * 模块名，即一级目录
     * <p>
     * 例如说，system、infra、tool 等等
     */
    private String moduleName;
    /**
     * 业务名，即二级目录
     * <p>
     * 例如说，user、permission、dict 等等
     */
    private String businessName;
    /**
     * 类名称（首字母大写）
     * <p>
     * 例如说，SysUser、SysMenu、SysDictData 等等
     */
    private String className;
    /**
     * 类描述
     */
    private String classComment;
    /**
     * 作者
     */
    private String author;

    // ========== 生成相关字段 ==========

    /**
     * 模板类型
     * <p>
     * 枚举 {@link CodegenTemplateTypeEnum}
     */
    private Integer templateType;

    // ========== 菜单相关字段 ==========

    /**
     * 父菜单编号
     * <p>
     * 关联 MenuDO 的 id 属性
     */
    private Long parentMenuId;

}
