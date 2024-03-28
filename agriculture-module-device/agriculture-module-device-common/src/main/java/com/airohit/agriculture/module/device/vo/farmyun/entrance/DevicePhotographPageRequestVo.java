package com.airohit.agriculture.module.device.vo.farmyun.entrance;

import com.airohit.agriculture.module.device.vo.farmyun.PageReqVo;
import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 16:10
 */
@Data
public class DevicePhotographPageRequestVo extends PageReqVo {

    /**
     * 开始时间（时间戳）
     */
    private String beginTime;
    /**
     * 结束时间（时间戳）
     */
    private String endTime;
    /**
     * 设备地址
     */
    private String deviceAddr;
}
