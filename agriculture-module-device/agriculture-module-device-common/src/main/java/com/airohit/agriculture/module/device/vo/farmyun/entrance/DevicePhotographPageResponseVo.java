package com.airohit.agriculture.module.device.vo.farmyun.entrance;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 16:12
 */
@Data
public class DevicePhotographPageResponseVo {


    /**
     * recordId : 23
     * deviceAddr : 4b5066c2Wfd28W4137Wbc1cW9cfda56c806e
     * type : 1
     * thumbnailsUrl : http://camera.farm.0531yun.cn/file/4b5066c2Wfd28W4137Wbc1cW9cfda56c806e_5e848dee1d3240e784d4864206a950b0.jpg
     * sourceImageUrl : http://images.jnrsmcu.com/farmCameraImages/4b5066c2Wfd28W4137Wbc1cW9cfda56c806e_5e848dee1d3240e784d4864206a950b0.jpg
     * recordtime : 2023-03-19 12:00:20
     */

    private int recordId;
    private String deviceAddr;
    private int type;
    private String thumbnailsUrl;
    private String sourceImageUrl;
    private String recordtime;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getThumbnailsUrl() {
        return thumbnailsUrl;
    }

    public void setThumbnailsUrl(String thumbnailsUrl) {
        this.thumbnailsUrl = thumbnailsUrl;
    }

    public String getSourceImageUrl() {
        return sourceImageUrl;
    }

    public void setSourceImageUrl(String sourceImageUrl) {
        this.sourceImageUrl = sourceImageUrl;
    }

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }
}
