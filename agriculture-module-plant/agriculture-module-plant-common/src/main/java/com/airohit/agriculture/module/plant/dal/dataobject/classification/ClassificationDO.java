package com.airohit.agriculture.module.plant.dal.dataobject.classification;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 病虫害识别 DO
 *
 * @author 管理员
 */
@TableName("diseases_classification")
@KeySequence("diseases_classification_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassificationDO extends BaseDO {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 病虫害名称
     */
    private String diseasesName;
    /**
     * 图片链接
     */
    private String imageUrl;
    /**
     * 作物类型,0水稻
     */
    private String cropType;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 农户ID
     */
    private Integer peasantId;
    /**
     * 农场编号
     */
    private Long farmTenantId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 地块ID
     */
    private Integer landId;
    /**
     * 经纬度
     */
    private String latitudeLongitude;
    /**
     * 用户经纬度
     */
    private String userLatitudeLongitude;
    /**
     * 措施ID
     */
    private Integer measureId;
    /**
     * 几率
     */
    private String odds;
    /**
     * 几率
     */
    private Integer isSave;

    /**
     * 1是0否
     */
    private Integer isPc;
}
