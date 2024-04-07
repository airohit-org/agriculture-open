package com.airohit.agriculture.module.statistics.dal.dataobject.farmInfo;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

@TableName("obs_device")
@KeySequence("obs_device_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDO extends BaseDO  {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 设备厂商
     */
    private Integer firmId;
    /**
     * 农场厂商id
     */
    private Integer farmFirmId;
    /**
     * 设备地址
     */
    private String deviceAddr;
    /**
     * 区域id
     */
    private String groupId;
    /**
     * 地块id
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer landId;
    /**
     * 农场id
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer farmId;
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 经度
     */
    private String devicelng;
    /**
     * 纬度
     */
    private String devicelat;
    /**
     * 设备iccid
     */
    private String deviceIccId;
    /**
     * 设备状态
     */
    private String status;
    /**
     * 租户编号
     */
    private String tenantId;
    /**
     * 备注
     */
    private String remark;

    /**
     * sim卡号
     */
    private String simNumber;
    /**
     * 设备服务ip
     */
    private String deviceServiceIp;
    /**
     * 设备服务端口
     */
    private Integer deviceServicePort;
    /**
     * 设备服务模版
     */
    private String deviceServiceTemplate;

}
