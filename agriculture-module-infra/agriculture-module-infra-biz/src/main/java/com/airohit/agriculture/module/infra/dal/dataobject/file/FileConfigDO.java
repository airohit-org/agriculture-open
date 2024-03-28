package com.airohit.agriculture.module.infra.dal.dataobject.file;

import com.airohit.agriculture.framework.file.core.client.FileClientConfig;
import com.airohit.agriculture.framework.file.core.enums.FileStorageEnum;
import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

/**
 * 文件配置表
 *
 * @author shiminghao
 */
@TableName(value = "infra_file_config", autoResultMap = true)
@KeySequence("infra_file_config_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileConfigDO extends BaseDO {

    /**
     * 配置编号，数据库自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 配置名
     */
    private String name;
    /**
     * 存储器
     * <p>
     * 枚举 {@link FileStorageEnum}
     */
    private Integer storage;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否为主配置
     * <p>
     * 由于我们可以配置多个文件配置，默认情况下，使用主配置进行文件的上传
     */
    private Boolean master;

    /**
     * 支付渠道配置
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private FileClientConfig config;

}
