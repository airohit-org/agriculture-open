package com.airohit.agriculture.module.device.vo.farmyun.spore;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 16:43
 */
public class SporeDataAndSporeDataAIByPageResponseVo {


    /**
     * recordId : 3847
     * deviceAddr : 0415220001
     * analyst : null
     * createTime : 2022-08-05 16:54:30
     * verifyTime : 2022-08-12 11:30:33
     * remark : null
     * imagesUrl : /static/sporeImages/0805165437925.jpg
     */

    private int recordId;
    private String deviceAddr;
    private String analyst;
    private String createTime;
    private String verifyTime;
    private String remark;
    private String imagesUrl;

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

    public String getAnalyst() {
        return analyst;
    }

    public void setAnalyst(String analyst) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(String imagesUrl) {
        this.imagesUrl = imagesUrl;
    }
}
