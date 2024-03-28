package com.airohit.agriculture.module.device.vo.farmyun.met;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 15:11
 */
@Data
public class TargetNodeInfoResponseVo {


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
     * regulatingInfoList : null
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
    private double temRatio;
    private double temOffset;
    private double temUpperLimit;
    private double temLowerLimit;
    private String humName;
    private String humUnit;
    private double humRatio;
    private double humOffset;
    private double humUpperLimit;
    private double humLowerLimit;
    private String switchOnContent;
    private String switchOffContent;
    private int switchAlarmType;


}
