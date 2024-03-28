package com.airohit.agriculture.module.device.vo.obs;

import lombok.Data;

import java.time.LocalDateTime;

/*
 * @Description:
 * @Author: hanliyao
 * @Date: 2023/7/13 15:23
 */
@Data
public class ObsDeviceApiVo {

    private Integer id;

    private String deviceFirm;

    private Integer firmId;

    private String deviceAddr;

    private String groupId;

    private Integer farmId;

    private String farmName;

    private Integer landId;

    private String landName;

    private String deviceType;

    private String deviceName;

    private String devicelng;

    private String devicelat;

    private String status;

    private LocalDateTime createTime;

    private String remark;

    private String simNumber;

    private String deviceServiceIp;

    private Integer deviceServicePort;

    private String deviceServiceTemplate;
}
