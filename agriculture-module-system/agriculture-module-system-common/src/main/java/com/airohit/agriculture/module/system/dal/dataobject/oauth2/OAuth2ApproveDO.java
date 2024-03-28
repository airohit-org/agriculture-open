package com.airohit.agriculture.module.system.dal.dataobject.oauth2;

import com.airohit.agriculture.framework.common.enums.UserTypeEnum;
import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * OAuth2 批准 DO
 * <p>
 * 用户在 sso.vue 界面时，记录接受的 scope 列表
 *
 * @author shiminghao
 */
@TableName(value = "system_oauth2_approve", autoResultMap = true)
@KeySequence("system_oauth2_approve_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
public class OAuth2ApproveDO extends BaseDO {

    /**
     * 编号，数据库自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;
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
     * 关联 {@link main.java.com.airohit.agriculture.module.system.dal.dataobject.oauth2.OAuth2ClientDO#getId()}
     */
    private String clientId;
    /**
     * 授权范围
     */
    private String scope;
    /**
     * 是否接受
     * <p>
     * true - 接受
     * false - 拒绝
     */
    private Boolean approved;
    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;

}
