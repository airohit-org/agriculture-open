package com.airohit.agriculture.module.land.dal.dataobject.crops;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 地块作物 DO
 *
 * @author 管理员
 */
@TableName("land_crops")
@KeySequence("land_crops_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CropsDO extends BaseDO {

    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 地块编号
     */
    private Integer landId;
    /**
     * 种植作物
     */
    private String crops;
    /**
     * 作物品种
     */
    private String cropsType;
    /**
     * 状态（0正常 1关闭）
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 种植作物是否其他 0 否 1 是
     */
    private Integer cropsIsOther;
    /**
     * 种植作物其他内容
     */
    private String cropsOtherContent;
    /**
     * 种植品种是否其他 0 否 1 是
     */
    private Integer cropsTypeIsOther;
    /**
     * 种植品种其他内容
     */
    private String cropsTypeOtherContent;

}
