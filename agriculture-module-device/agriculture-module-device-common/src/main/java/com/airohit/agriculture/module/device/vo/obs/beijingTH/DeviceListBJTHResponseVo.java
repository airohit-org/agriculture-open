package com.airohit.agriculture.module.device.vo.obs.beijingTH;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/5 13:27
 */
@Data
public class DeviceListBJTHResponseVo {

    /**
     "id": 789,
     "name": "农场环境感知生态观测站-水稻1",
     "iccid": "898604A21921C2415013",
     "version": "2.0",
     "status": "normal",
     "battery": "13.81085",
     "signal": "99",
     "active": 1,
     "device_type": "1",
     "sn": "07cbb96f9f37980ba78b957dfd6753de",
     "lat": 47.593725,
     "lon": 131.992268,
     "alt": 54,
     "addr": "黑龙江省鹤岗市绥滨县290农场水稻田中部",

     */

    /**
     * 设备id
     */
    private String id;


    private String iccid;

    /**
     * 设备名称
     */
    private String name;

    private String version;

    private String status;

    private String battery;

    private String signal;

    private String active;

    private String device_type;

    private String sn;

    private String lat;

    private String lon;

    private String alt;

    private String addr;

}
