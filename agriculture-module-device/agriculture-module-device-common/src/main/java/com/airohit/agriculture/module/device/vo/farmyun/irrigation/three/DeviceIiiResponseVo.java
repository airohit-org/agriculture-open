package com.airohit.agriculture.module.device.vo.farmyun.irrigation.three;

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
         * factorId : 40165167_3
         * factorNo : 3
         * factorType : 1
         * deviceAddr : 40165167
         * nodeType : 1
         * factorName : 土壤温度
         * enabled : 1
         * unit : ℃
         * digits : 1
         * coefficient : 0.1
         * offset : 0
         * upperLimit : 100
         * lowerLimit : 0
         * electricLowerlimit : 5
         * smsEnabled : 0
         * emailEnabled : 0
         * offlineAlarmingSwitch : 0
         * offlineAlarmingAlarmcontent : null
         * electricLimitSwitch : 0
         * electricLimitAlarmcontent : null
         * excessAlarmingSwitch : 0
         * excessAlarmingAlarmcontent : null
         * createTime : 1660547896000
         */

        private String factorId;
        private int factorNo;
        private String factorType;
        private String deviceAddr;
        private int nodeType;
        private String factorName;
        private String enabled;
        private String unit;
        private int digits;
        private double coefficient;
        private int offset;
        private int upperLimit;
        private int lowerLimit;
        private int electricLowerlimit;
        private String smsEnabled;
        private String emailEnabled;
        private String offlineAlarmingSwitch;
        private String offlineAlarmingAlarmcontent;
        private String electricLimitSwitch;
        private String electricLimitAlarmcontent;
        private String excessAlarmingSwitch;
        private String excessAlarmingAlarmcontent;
        private long createTime;


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
