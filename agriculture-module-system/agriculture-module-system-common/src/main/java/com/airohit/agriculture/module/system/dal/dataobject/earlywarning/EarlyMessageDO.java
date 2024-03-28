package com.airohit.agriculture.module.system.dal.dataobject.earlywarning;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 预警消息 DO
 *
 * @author 史铭浩
 */
@TableName("early_warning")
@KeySequence("early_warning_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("看预警")
public class EarlyMessageDO extends BaseDO {

    /**
     * Id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("id")
    private Integer id;
    /**
     * 预警类型
     */
    @ApiModelProperty("类型,0气象预警,2虫害预警,3地块预警")
    private Integer warningType;
    /**
     * 预警消息
     */
    @ApiModelProperty("消息")
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
     * 预防名称
     */
    @ApiModelProperty("预防名称")
    private String preventName;
    /**
     * 预防办法
     */
    @ApiModelProperty("预防办法")
    private String preventMethod;
    /**
     * 症状
     */
    @ApiModelProperty("症状")
    private String symptom;

}
