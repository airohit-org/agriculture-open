package com.airohit.agriculture.module.device.dal.dataobject.land;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 地块下的设备 DO
 *
 * @author shiminghao
 */
@TableName("device_land")
@KeySequence("device_land_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceLandDO extends BaseDO {

    /**
     * Id
     */
    @TableId
    private Integer id;
    /**
     * 坐标
     */
    private String coordinate;
    /**
     * 设备ID
     */
    private Integer deviceId;
    /**
     * 地块ID
     */
    private Integer landId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 设备sn
     */
    private String deviceSn;
    /**
     * 是否在线,1是0否
     */
    private Integer status;

}
