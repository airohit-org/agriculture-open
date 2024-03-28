package com.airohit.agriculture.module.land.dal.dataobject.land;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 地块信息 DO
 *
 * @author 管理员
 */
@TableName("land")
@KeySequence("land_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LandDO extends BaseDO {

    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 地块名称
     */
    private String landName;
    /**
     * 种植作物
     */
    private String crops;
    /**
     * 作物品种
     */
    private String cropsType;
    /**
     * 颜色
     */
    private String color;
    /**
     * 种植面积
     */
    private Double area;
    /**
     * 联系人
     */
    private String contacts;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 地块坐标
     */
    private String landCoordinate;
    /**
     * 地块形状
     */
    private String landForm;
    /**
     * 坐标中心点
     */
    private String landCoordinateCenter;
    /**
     * 状态（0正常 1关闭）
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;


    /**
     * 种植作物是否其它 0 否 1 是
     */
    private Integer cropsIsOther;

    /**
     * 种植作物其它内容
     */
    private String cropsOtherContent;

    /**
     * 种植品种是否其它 0 否 1 是
     */
    private Integer cropsTypeIsOther;

    /**
     * 种植品种其它内容
     */
    private String cropsTypeOtherContent;

    /**
     * 地块形状
     */
    private String landShapeImage;

    /**
     * 遥感分割地块id
     */
    private Integer fid;

}
