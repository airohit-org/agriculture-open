package com.airohit.agriculture.module.system.dal.dataobject.social;

import com.airohit.agriculture.framework.common.enums.UserTypeEnum;
import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 社交用户的绑定
 * 即 {@link main.java.com.airohit.agriculture.module.system.dal.dataobject.social.SocialUserDO} 与 UserDO 的关联表
 *
 * @author shiminghao
 */
@TableName(value = "system_social_user_bind", autoResultMap = true)
@KeySequence("system_social_user_bind_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialUserBindDO extends BaseDO {

    /**
     * 关联的用户编号
     * <p>
     * 关联 UserDO 的编号
     */
    @TableId(type = IdType.AUTO)
    private Long userId;
    /**
     * 用户类型
     * <p>
     * 枚举 {@link UserTypeEnum}
     */
    private Integer userType;

    /**
     * 社交平台的用户编号
     * <p>
     * 关联 {@link main.java.com.airohit.agriculture.module.system.dal.dataobject.social.SocialUserDO#getId()}
     */
    private Long socialUserId;
    /**
     * 社交平台的类型
     * <p>
     * 冗余 {@link main.java.com.airohit.agriculture.module.system.dal.dataobject.social.SocialUserDO#getType()}
     */
    private Integer socialType;

}
