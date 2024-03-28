package com.airohit.agriculture.module.device.vo.farmyun.userinfo;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/4 10:05
 */
@Data
public class AlarmNoticeRecordRequestVo {
    /**
     * 凯斯时间
     */
    private String beginTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 设备地址码
     */
    private String deviceAddr;
    /**
     * 节点id,(非必填)
     */
    private String factorIds;
    /**
     * 通知类型 0邮件 1短信(非必填)
     */
    private String noticeType;
}
