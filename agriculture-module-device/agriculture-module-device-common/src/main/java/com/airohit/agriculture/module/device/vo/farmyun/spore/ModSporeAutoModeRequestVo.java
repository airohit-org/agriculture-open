package com.airohit.agriculture.module.device.vo.farmyun.spore;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 16:31
 */
@Data
public class ModSporeAutoModeRequestVo {
    private String deviceAddr;
    private String beginTime;
    private String runHour;
    private String samplingTime;
    private String workInterval;
    private int singlePulse;
    private int pulseUpperLimit;
}
