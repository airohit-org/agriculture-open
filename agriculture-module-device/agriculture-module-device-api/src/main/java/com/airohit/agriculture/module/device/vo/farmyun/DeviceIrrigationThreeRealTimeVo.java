package com.airohit.agriculture.module.device.vo.farmyun;

import lombok.Data;

import java.util.List;

/**
 * Created with IDEA
 * 智慧环控3.0设备实时数据
 *
 * @author :shiminghao
 * @date :2023/5/5 13:53
 */
@Data
public class DeviceIrrigationThreeRealTimeVo {
    /**
     * 是否报警，0正常 1为数值报警 2电量报警 3电量数值同时报警 4离线报警
     */
    private int alarming;
    /**
     * 是否遥调   1是 0不是
     */
    private int isRegulating;
    /**
     * 节点编号
     */
    private int factorNo;
    /**
     * 超限报警 1超上限    2超下限
     */
    private int excessAlarmingFlag;
    /**
     * 信号
     */
    private String factorSignal;
    /**
     * 超限报警开关 0:关 ； 1:开
     */
    private int excessAlarmingSwitch;
    /**
     * 阀门状态 1:打开，0:关闭
     */
    private String valveStatus;
    /**
     * 节点使能，0,关闭；1,打开
     */
    private int enabled;
    /**
     * 节点状态   online offline alarming
     */
    private String factorStatus;
    /**
     * 节点名称
     */
    private String factorName;
    /**
     * 节点类型  1采集器 2阀门
     */
    private String factorType;
    /**
     * 纬度
     */
    private double lat;
    /**
     * 经度
     */
    private double lng;
    /**
     * 是否遥调报警 0不报警 1报警
     */
    private int regulatingAlarm;
    /**
     * 节点id
     */
    private String factorId;
    /**
     * 电量
     */
    private String electricQuantity;
    /**
     * 单位
     */
    private String unit;
    /**
     * 运行模式 模式，1为手动，2为自动，3为定时-定点，4为定时-星期 5为自动--平均值
     */
    private int factorMode;
    /**
     * 遥调报警信息
     */

    private List<FactorDetail> ifrList;

    @Data
    public static class FactorDetail {
        /**
         * 节点id
         */
        private String factorId;
        /**
         * 遥调值
         */
        private double regularValue;
        /**
         * 展示值
         */
        private String regularText;
        /**
         * 报警等级 0不报警 1报警
         */
        private int alarmLevel;

    }


}
