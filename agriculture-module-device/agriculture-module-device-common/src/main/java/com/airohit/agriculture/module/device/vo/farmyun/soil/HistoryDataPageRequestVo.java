package com.airohit.agriculture.module.device.vo.farmyun.soil;

import com.airohit.agriculture.module.device.vo.farmyun.PageReqVo;
import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 15:52
 */
@Data
public class HistoryDataPageRequestVo extends PageReqVo {
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
    /**
     * 节点编号（多个用英文逗号分割）(非必填)
     */
    private String nodeId;
}
