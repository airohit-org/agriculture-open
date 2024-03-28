package com.airohit.agriculture.module.device.vo.farmyun.irrigation.two;

import lombok.Data;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/6 14:02
 */
@Data
public class DeviceIiiResponseVo {


    /**
     * deviceAddr : 40165167
     * deviceType : irrigation
     * deviceName : LoRa无线采集灌溉系统（展厅设备勿动）
     * devicelng : 115.95706
     * devicelat : 39.062559
     * saveDateInterval : 1
     * offlineInterval : 11
     * city : 济南
     * createTime : 1643243753000
     * alertDataStatus : 0
     * phoneOfflineNotification : 0
     * phoneAlarmInterval : 10
     * phoneMaxSendingNumber : 5
     * emailOfflineNotification : 0
     * emailAlarmInterval : 1
     * emailMaxSendingNumber : 3
     * phone : null
     * email : null
     * deviceEnabled : 1
     * deviceIccId : null
     * groupId : null
     * authority : null
     * listIrrigationFactor : [{"factorId":"40165167_3","factorNo":3,"factorType":"1","deviceAddr":"40165167","nodeType":1,"factorName":"土壤温度","enabled":"1","unit":"℃","digits":1,"coefficient":0.1,"offset":0,"upperLimit":100,"lowerLimit":0,"electricLowerlimit":5,"smsEnabled":"0","emailEnabled":"0","offlineAlarmingSwitch":"0","offlineAlarmingAlarmcontent":null,"electricLimitSwitch":"0","electricLimitAlarmcontent":null,"excessAlarmingSwitch":"0","excessAlarmingAlarmcontent":null,"createTime":1660547896000},{"factorId":"40165167_66","factorNo":66,"factorType":"2","deviceAddr":"40165167","nodeType":1,"factorName":"LORA阀门控制器（继电器输出）","enabled":"1","unit":"","digits":1,"coefficient":0.1,"offset":0,"upperLimit":100,"lowerLimit":0,"electricLowerlimit":5,"smsEnabled":"0","emailEnabled":"0","offlineAlarmingSwitch":"0","offlineAlarmingAlarmcontent":null,"electricLimitSwitch":"0","electricLimitAlarmcontent":null,"excessAlarmingSwitch":"0","excessAlarmingAlarmcontent":null,"createTime":1660547896000}]
     * listTbIrrigationContact : []
     */

    private String deviceAddr;
    private String deviceName;
    private double devicelng;
    private double devicelat;
    private int saveDateInterval;
    private int offlineInterval;
    private long createTime;
    private String alertDataStatus;
    private int phoneOfflineNotification;
    private int phoneAlarmInterval;
    private int phoneMaxSendingNumber;
    private int emailOfflineNotification;
    private int emailAlarmInterval;
    private int emailMaxSendingNumber;
    private List<ListIrrigationFactor> listIrrigationFactor;
    private List<listTbIrrigationContact> listTbIrrigationContact;


    @Data
    public static class ListIrrigationFactor {


        /**
         * nodeId : 1
         * deviceAddr : 66668888
         * deviceName : 66668888
         * factorId : 66668888_1
         * nodeName : 节点1
         * enable : 1
         * factorType : 1
         * nodeMold : 0
         * nodeType : 1
         * priority : 0
         * digits : 1
         * temName : 温度
         * temUnit : ℃
         * temRatio : 0.1
         * temOffset : 0
         * temUpperLimit : 100
         * temLowerLimit : 0
         * humName : 湿度
         * humUnit : %RH
         * humRatio : 0.1
         * humOffset : 0
         * humUpperLimit : 100
         * humLowerLimit : 0
         * switchOnContent : null
         * switchOffContent : null
         * switchAlarmType : 0
         * smsEnabled : 0
         * emailEnabled : 0
         * offlineAlarmingSwitch : 0
         * offlineAlarmingAlarmContent : [设备名称]-[节点名称]设备地址:[设备地址],节点离线,系统时间:[系统时间]
         * excessAlarmingSwitch : 0
         * excessAlarmingAlarmContent : [设备名称]-[节点名称]设备地址:[设备地址],[报警值],[报警限值]系统时间:[系统时间]
         * createTime : 2022-09-16 13:28:14
         * listFactorRegulating : null
         */

        private int nodeId;
        private String deviceAddr;
        private String factorId;
        private String nodeName;
        private int enable;
        private int factorType;
        private int nodeType;
        private int priority;
        private int digits;
        private String temName;
        private String temUnit;
        private double temRatio;
        private int temOffset;
        private int temUpperLimit;
        private int temLowerLimit;
        private String humName;
        private String humUnit;
        private double humRatio;
        private int humOffset;
        private int humUpperLimit;
        private int humLowerLimit;
        private String switchOnContent;
        private String switchOffContent;
        private int switchAlarmType;
        private int smsEnabled;
        private int emailEnabled;
        private int excessAlarmingSwitch;
        private String excessAlarmingAlarmContent;
        private String createTime;

        public int getNodeId() {
            return nodeId;
        }

        public void setNodeId(int nodeId) {
            this.nodeId = nodeId;
        }

        public String getDeviceAddr() {
            return deviceAddr;
        }

        public void setDeviceAddr(String deviceAddr) {
            this.deviceAddr = deviceAddr;
        }


        public String getFactorId() {
            return factorId;
        }

        public void setFactorId(String factorId) {
            this.factorId = factorId;
        }

        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public int getFactorType() {
            return factorType;
        }

        public void setFactorType(int factorType) {
            this.factorType = factorType;
        }


        public int getNodeType() {
            return nodeType;
        }

        public void setNodeType(int nodeType) {
            this.nodeType = nodeType;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public int getDigits() {
            return digits;
        }

        public void setDigits(int digits) {
            this.digits = digits;
        }

        public String getTemName() {
            return temName;
        }

        public void setTemName(String temName) {
            this.temName = temName;
        }

        public String getTemUnit() {
            return temUnit;
        }

        public void setTemUnit(String temUnit) {
            this.temUnit = temUnit;
        }

        public double getTemRatio() {
            return temRatio;
        }

        public void setTemRatio(double temRatio) {
            this.temRatio = temRatio;
        }

        public int getTemOffset() {
            return temOffset;
        }

        public void setTemOffset(int temOffset) {
            this.temOffset = temOffset;
        }

        public int getTemUpperLimit() {
            return temUpperLimit;
        }

        public void setTemUpperLimit(int temUpperLimit) {
            this.temUpperLimit = temUpperLimit;
        }

        public int getTemLowerLimit() {
            return temLowerLimit;
        }

        public void setTemLowerLimit(int temLowerLimit) {
            this.temLowerLimit = temLowerLimit;
        }

        public String getHumName() {
            return humName;
        }

        public void setHumName(String humName) {
            this.humName = humName;
        }

        public String getHumUnit() {
            return humUnit;
        }

        public void setHumUnit(String humUnit) {
            this.humUnit = humUnit;
        }

        public double getHumRatio() {
            return humRatio;
        }

        public void setHumRatio(double humRatio) {
            this.humRatio = humRatio;
        }

        public int getHumOffset() {
            return humOffset;
        }

        public void setHumOffset(int humOffset) {
            this.humOffset = humOffset;
        }

        public int getHumUpperLimit() {
            return humUpperLimit;
        }

        public void setHumUpperLimit(int humUpperLimit) {
            this.humUpperLimit = humUpperLimit;
        }

        public int getHumLowerLimit() {
            return humLowerLimit;
        }

        public void setHumLowerLimit(int humLowerLimit) {
            this.humLowerLimit = humLowerLimit;
        }

        public String getSwitchOnContent() {
            return switchOnContent;
        }

        public void setSwitchOnContent(String switchOnContent) {
            this.switchOnContent = switchOnContent;
        }

        public String getSwitchOffContent() {
            return switchOffContent;
        }

        public void setSwitchOffContent(String switchOffContent) {
            this.switchOffContent = switchOffContent;
        }

        public int getSwitchAlarmType() {
            return switchAlarmType;
        }

        public void setSwitchAlarmType(int switchAlarmType) {
            this.switchAlarmType = switchAlarmType;
        }

        public int getSmsEnabled() {
            return smsEnabled;
        }

        public void setSmsEnabled(int smsEnabled) {
            this.smsEnabled = smsEnabled;
        }

        public int getEmailEnabled() {
            return emailEnabled;
        }

        public void setEmailEnabled(int emailEnabled) {
            this.emailEnabled = emailEnabled;
        }


        public int getExcessAlarmingSwitch() {
            return excessAlarmingSwitch;
        }

        public void setExcessAlarmingSwitch(int excessAlarmingSwitch) {
            this.excessAlarmingSwitch = excessAlarmingSwitch;
        }

        public String getExcessAlarmingAlarmContent() {
            return excessAlarmingAlarmContent;
        }

        public void setExcessAlarmingAlarmContent(String excessAlarmingAlarmContent) {
            this.excessAlarmingAlarmContent = excessAlarmingAlarmContent;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

    }

    @Data
    public static class listTbIrrigationContact {
        private String deviceAddr;
        private String contactType;
        private String contact;
        private long updateTime;
        private int id;
    }
}
