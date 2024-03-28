package com.airohit.agriculture.module.land.dal.dataobject.varieties;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 品种管理 DO
 *
 * @author shiminghao
 */
@TableName("crops_varieties")
@KeySequence("crops_varieties_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CropsVarietiesDO extends BaseDO {

    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 作物品种名称
     */
    private String cropsVarietiesName;
    /**
     * 作物品种图标
     */
    private String imageUrl;
    /**
     * 种植作物编号
     */
    private Integer raiseCropsId;
    /**
     * 状态（0正常 1关闭）
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 编码
     */
    private String code;
    /**
     * 国家审定编号
     */
    private String countryAuthCode;
    /**
     * 选育机构
     */
    private String breedingAgency;
    /**
     * 品种来源
     */
    private String varietiesSource;
    /**
     * 特征特性
     */
    private String feature;
    /**
     * 产量表现
     */
    private String production;
    /**
     * 栽培技术
     */
    private String cultivationTechnique;
    /**
     * 适应区域
     */
    private String adaptationZone;


    /**
     * 数据code
     */
    private String dataCode;

}
