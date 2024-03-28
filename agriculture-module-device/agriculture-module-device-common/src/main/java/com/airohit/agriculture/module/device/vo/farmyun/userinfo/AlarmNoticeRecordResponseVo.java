package com.airohit.agriculture.module.device.vo.farmyun.userinfo;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/4 10:05
 */
@Data
public class AlarmNoticeRecordResponseVo {


    /**
     * noticeID : 147
     * noticeIType : 0
     * deviceAddr : 88888888
     * factorName : 空气温度
     * factorId : 88888888_1
     * contact : 2219949697@qq.com
     * noticeIContent : 超限报警:  演示2022-空气温度设备地址:88888888,超下限 当前值0.0,正常范围：5.0~20.0设备时间:2022-04-15 20:14:51。
     * remark : null
     * createTime : 2022-04-15 20:14:52
     */
    /**
     * 通知记录表id
     */
    private int noticeID;
    /**
     * 类型 0邮件 1短信
     */
    private int noticeIType;
    /**
     * 设备地址
     */
    private String deviceAddr;
    /**
     * 节点名称
     */
    private String factorName;
    /**
     * 节点id
     */
    private String factorId;
    /**
     * 联系人
     */
    private String contact;
    /**
     * 通知内容
     */
    private String noticeIContent;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private String createTime;

    public int getNoticeID() {
        return noticeID;
    }

    public void setNoticeID(int noticeID) {
        this.noticeID = noticeID;
    }

    public int getNoticeIType() {
        return noticeIType;
    }

    public void setNoticeIType(int noticeIType) {
        this.noticeIType = noticeIType;
    }

    public String getDeviceAddr() {
        return deviceAddr;
    }

    public void setDeviceAddr(String deviceAddr) {
        this.deviceAddr = deviceAddr;
    }

    public String getFactorName() {
        return factorName;
    }

    public void setFactorName(String factorName) {
        this.factorName = factorName;
    }

    public String getFactorId() {
        return factorId;
    }

    public void setFactorId(String factorId) {
        this.factorId = factorId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNoticeIContent() {
        return noticeIContent;
    }

    public void setNoticeIContent(String noticeIContent) {
        this.noticeIContent = noticeIContent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
