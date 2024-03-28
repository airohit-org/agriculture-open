package com.airohit.agriculture.module.device.vo.farmyun.soil;

import lombok.Data;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 14:56
 */
@Data
public class DeviceAllInfoResponseVo {


    /**
     * deviceAddr : 10000000
     * deviceName : 气象10000000
     * deviceType : null
     * lng : 11.097684058355
     * lat : 40.0121708354832
     * saveDataInterval : 10
     * offlineInterval : 8
     * city : 济南
     * offlineNoticeType : null
     * deviceEnabled : null
     * nodeInfoList : [{"nodeId":14,"deviceAddr":"10000000","nodeName":"开关量","enable":1,"nodeMold":0,"nodeType":5,"priority":2,"digits":2,"temName":"闭合显示内容","temUnit":"","temRatio":1,"temOffset":0,"temUpperLimit":200000,"temLowerLimit":0,"humName":"断开显示内容","humUnit":"%","humRatio":1,"humOffset":0,"humUpperLimit":100,"humLowerLimit":0,"switchOnContent":"断开","switchOffContent":"闭合","switchAlarmType":1,"regulatingInfoList":[]}]
     */

    private String deviceAddr;
    private String deviceName;
    private double lng;
    private double lat;
    private int saveDataInterval;
    private int offlineInterval;
    private List<NodeInfoList> nodeInfoList;


    @Data
    public static class NodeInfoList {
        /**
         * nodeId : 14
         * deviceAddr : 10000000
         * nodeName : 开关量
         * enable : 1
         * nodeMold : 0
         * nodeType : 5
         * priority : 2
         * digits : 2
         * temName : 闭合显示内容
         * temUnit :
         * temRatio : 1
         * temOffset : 0
         * temUpperLimit : 200000
         * temLowerLimit : 0
         * humName : 断开显示内容
         * humUnit : %
         * humRatio : 1
         * humOffset : 0
         * humUpperLimit : 100
         * humLowerLimit : 0
         * switchOnContent : 断开
         * switchOffContent : 闭合
         * switchAlarmType : 1
         * regulatingInfoList : []
         */

        private int nodeId;
        private String deviceAddr;
        private String nodeName;
        private int enable;
        private int nodeMold;
        private int nodeType;
        private int priority;
        private int digits;
        private String temName;
        private String temUnit;
        private int temRatio;
        private int temOffset;
        private int temUpperLimit;
        private int temLowerLimit;
        private String humName;
        private String humUnit;
        private int humRatio;
        private int humOffset;
        private int humUpperLimit;
        private int humLowerLimit;
        private String switchOnContent;
        private String switchOffContent;
        private int switchAlarmType;
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
}
