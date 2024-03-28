package com.airohit.agriculture.module.device.vo.farmyun;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created with IDEA
 * 摄像头设备实时数据
 *
 * @author :shiminghao
 * @date :2023/5/5 13:53
 */
@Data
public class DeviceCameraRealTimeVo {

    private String deviceAddr;//设备地址
    private String cameraName;//摄像头名称
    private String rtmpUrl;//摄像头rtmpUrl地址
    private String httpUrl;//摄像头httpUrl地址
    private Integer type;// 是否支持云台 0：否  1：是
    private String appKey;//key
    private String secret;//秘钥
    private String accessToken;//token
    private String expireTime;//token过期时间
    private String deviceSerial;//设备序列号
    private Integer channelNo;//通道
    private String validateCode; //验证码
    private Integer autoPlay;//是否自动播放 0：否  1：是
    private Integer audioEnable;//是否播放音频 0：否  1：是
    private Integer playType;//播放类型 0：预览
    private Integer definition;//清晰度 0：高清   1：标清
    private Integer playTemplate;//播放器模板 0：极简版 1：标准版 2：安防版
    private Integer playMode;//播放模式 0：仅直播 1：支持EZOPEN协议
    private BigDecimal devicelng; //经度
    private BigDecimal devicelat; //纬度
}
