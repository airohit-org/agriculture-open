package com.airohit.agriculture.module.device.vo.farmyun.userinfo;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 15:03
 */
@Data
public class DeviceCameraResponseVo {


    /**
     * deviceAddr : 376fe700-9bf7-11eb-9537-0f9adbb7
     * cameraName : 测试摄像头
     * enabled : null
     * rtmpUrl : https://hls01open.ys7.com/openlive/6e0b2be040a943489ef0b9bb344b96b8.hd.m3u8
     * httpUrl : https://flvopen.ys7.com:9188/openlive/6e0b2be040a943489ef0b9bb344b96b8.hd.flv
     * type : null
     * appKey : null
     * secret : null
     * accessToken : null
     * expireTime : null
     * deviceSerial : null
     * channelNo : null
     * validateCode : null
     * autoPlay : null
     * audioEnable : null
     * playType : null
     * definition : null
     * playTemplate : null
     * playMode : 0
     * groupId : null
     * devicelng : null
     * devicelat : null
     */

    private String deviceAddr;
    private String cameraName;
    private String rtmpUrl;
    private String httpUrl;
    private int type;
    private String appKey;
    private String secret;
    private String accessToken;
    private String expireTime;
    private String deviceSerial;
    private int channelNo;
    private String validateCode;
    private int autoPlay;
    private int audioEnable;
    private int playType;
    private int definition;
    private int playTemplate;
    private int playMode;
    private double devicelng;
    private double devicelat;

}
