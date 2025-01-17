package com.airohit.agriculture.module.statistics.dal.dataobject.message;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 预警消息 DO
 *
 * @author 史铭浩
 */
@TableName("warning_message")
@KeySequence("warning_message_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarningMessageDO extends BaseDO {

    /**
     * Id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 预警类型
     */
    private Integer warningType;
    /**
     * 预警消息
     */
    private String warningMessage;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否全局,1是0否
     */
    private Integer overallSituation;
    /**
     * 用户ID
     */
    private Integer userId;


}
