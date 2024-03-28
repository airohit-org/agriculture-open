package com.airohit.agriculture.module.land.dal.dataobject.temperaturezone;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 积温带管理 DO
 *
 * @author shiminghao
 */
@TableName("accumulated_temperature_zone")
@KeySequence("accumulated_temperature_zone_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemperatureZoneDO extends BaseDO {

    /**
     * Id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 积温带名称
     */
    private String name;
    /**
     * 温度
     */
    private String temperature;
    /**
     * 温度表现
     */
    private String temperatureBehaviour;
    /**
     * 区域
     */
    private String region;
    /**
     * 备注
     */
    private String remark;
    /**
     * 数据code
     */

    private String dataCode;

}
