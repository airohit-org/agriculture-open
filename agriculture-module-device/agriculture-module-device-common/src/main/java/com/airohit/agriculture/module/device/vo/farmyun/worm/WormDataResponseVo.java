package com.airohit.agriculture.module.device.vo.farmyun.worm;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/6 13:47
 */
public class WormDataResponseVo {


    /**
     * recordId : 68958
     * deviceAddr : 20000009
     * analyst : null
     * createTime : 2022-08-07 21:15:59
     * verifyTime : 2000-01-01 08:00:00
     * remark : null
     * imagesUrl : /static/wormImages/20000009A_20220807211325.jpg
     * analyseCoordUrl : null
     * analyseData : null
     * isAnalyse : 0
     * imagesSize : 619708
     * analyseCoord : null
     * wormDataAIDO : null
     */

    private int recordId;
    private String deviceAddr;
    private Object analyst;
    private String createTime;
    private String verifyTime;
    private Object remark;
    private String imagesUrl;
    private Object analyseCoordUrl;
    private Object analyseData;
    private int isAnalyse;
    private String imagesSize;
    private Object analyseCoord;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getDeviceAddr() {
        return deviceAddr;
    }

    public void setDeviceAddr(String deviceAddr) {
        this.deviceAddr = deviceAddr;
    }

    public Object getAnalyst() {
        return analyst;
    }

    public void setAnalyst(Object analyst) {
        this.analyst = analyst;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(String verifyTime) {
        this.verifyTime = verifyTime;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public String getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(String imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public Object getAnalyseCoordUrl() {
        return analyseCoordUrl;
    }

    public void setAnalyseCoordUrl(Object analyseCoordUrl) {
        this.analyseCoordUrl = analyseCoordUrl;
    }

    public Object getAnalyseData() {
        return analyseData;
    }

    public void setAnalyseData(Object analyseData) {
        this.analyseData = analyseData;
    }

    public int getIsAnalyse() {
        return isAnalyse;
    }

    public void setIsAnalyse(int isAnalyse) {
        this.isAnalyse = isAnalyse;
    }

    public String getImagesSize() {
        return imagesSize;
    }

    public void setImagesSize(String imagesSize) {
        this.imagesSize = imagesSize;
    }

    public Object getAnalyseCoord() {
        return analyseCoord;
    }

    public void setAnalyseCoord(Object analyseCoord) {
        this.analyseCoord = analyseCoord;
    }
}
