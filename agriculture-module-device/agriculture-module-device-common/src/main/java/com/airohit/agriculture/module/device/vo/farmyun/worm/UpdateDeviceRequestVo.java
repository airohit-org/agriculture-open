package com.airohit.agriculture.module.device.vo.farmyun.worm;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 16:27
 */
@Data
public class UpdateDeviceRequestVo {
    private String deviceAddr;
    private String deviceName;
    private double lng;
    private double lat;
    private int saveDateInterval;
    private int offlineInterval;
}
