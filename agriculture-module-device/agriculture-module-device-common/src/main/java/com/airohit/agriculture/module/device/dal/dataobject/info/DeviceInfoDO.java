package com.airohit.agriculture.module.device.dal.dataobject.info;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 设备基本信息 DO
 *
 * @author shiminghao
 */
@TableName("device_info")
@KeySequence("device_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfoDO extends BaseDO {

    /**
     * id
     */
    @TableId(type = IdType.INPUT)
    private Integer id;
    /**
     * 设备型号
     */
    private String deviceType;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备描述
     */
    private String deviceDesc;
    /**
     * 设备功能
     */
    private String deviceFunc;
    /**
     * 样图
     */
    private String image;
    /**
     * 设备厂商
     */
    private String deviceCompany;
    /**
     * 设备本地通信方式
     */
    private String localCommunication;
    /**
     * 设备本地通信协议
     */
    private String localCommunicationProtocol;
    /**
     * 数据接入方式
     */
    private String accessMethod;
    /**
     * 接入平台协议
     */
    private String localPlatformProrocol;
    /**
     * 状态（0正常 1关闭）
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 数据表示code
     */
    private String dataCode;
}
