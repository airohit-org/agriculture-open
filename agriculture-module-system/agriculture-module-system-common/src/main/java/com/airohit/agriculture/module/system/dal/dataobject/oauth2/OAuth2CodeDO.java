package com.airohit.agriculture.module.system.dal.dataobject.oauth2;

import com.airohit.agriculture.framework.common.enums.UserTypeEnum;
import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * OAuth2 授权码 DO
 *
 * @author shiminghao
 */
@TableName(value = "system_oauth2_code", autoResultMap = true)
@KeySequence("system_oauth2_code_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
public class OAuth2CodeDO extends BaseDO {

    /**
     * 编号，数据库递增
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 授权码
     */
    private String code;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户类型
     * <p>
     * 枚举 {@link UserTypeEnum}
     */
    private Integer userType;
    /**
     * 客户端编号
     * <p>
     * 关联 {@link OAuth2ClientDO#getClientId()}
     */
    private String clientId;
    /**
     * 授权范围
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> scopes;
    /**
     * 重定向地址
     */
    private String redirectUri;
    /**
     * 状态
     */
    private String state;
    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;

}
