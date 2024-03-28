package com.airohit.agriculture.module.device.vo.farmyun.worm;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/6 13:34
 */
@Data
public class WormStatisticsByGroupRequestVo {
    /**
     * 区域ID
     */
    private String groupId;
    /**
     * 开始时间（yyyy-MM-dd HH:mm:ss）
     */
    private String beginTime;
    /**
     * 结束时间（yyyy-MM-dd HH:mm:ss）
     */
    private String endTime;
}
