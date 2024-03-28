package com.airohit.agriculture.module.device.vo.farmyun.irrigation.three;

import com.airohit.agriculture.module.device.vo.farmyun.PageReqVo;
import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 16:39
 */
@Data
public class DeviceHistoryListPageRequestVo extends PageReqVo {
    /**
     * 开始时间（yyyy-MM-dd HH:mm:ss）
     */
    private String beginTime;
    /**
     * 结束时间（yyyy-MM-dd HH:mm:ss）
     */
    private String endTime;
    /**
     * 设备地址
     */
    private String deviceAddr;
    /**
     * 节点id（多个用英文逗号分隔)(非必填)
     */
    private String factorId;
}
