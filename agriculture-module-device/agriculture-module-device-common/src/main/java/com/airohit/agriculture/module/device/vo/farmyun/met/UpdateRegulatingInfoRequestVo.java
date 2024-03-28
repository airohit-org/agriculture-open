package com.airohit.agriculture.module.device.vo.farmyun.met;

import lombok.Data;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 15:34
 */
@Data
public class UpdateRegulatingInfoRequestVo {
    private String deviceAddr;
    private int nodeId;
    private List<RegulatingInfo> regulatingInfoList;


    @Data
    public static class RegulatingInfo {
        private String regularId;
        private int nodeId;
        private String deviceAddr;
        private double regularValue;
        private String regularText;
        private int alarmEnable;
    }

}
