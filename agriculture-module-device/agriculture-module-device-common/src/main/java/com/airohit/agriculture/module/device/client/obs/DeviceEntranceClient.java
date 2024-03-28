package com.airohit.agriculture.module.device.client.obs;

import com.airohit.agriculture.module.device.vo.farmyun.PageResVo;
import com.airohit.agriculture.module.device.vo.farmyun.Result;
import com.airohit.agriculture.module.device.vo.farmyun.entrance.DevicePhotographPageRequestVo;
import com.airohit.agriculture.module.device.vo.farmyun.entrance.DevicePhotographPageResponseVo;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Header;
import com.dtflys.forest.annotation.Query;

/**
 * Created with IDEA
 * 苗情设备
 *
 * @author :shiminghao
 * @date :2023/5/5 16:10
 */
@BaseRequest(baseURL = "http://api.farm.0531yun.cn")
public interface DeviceEntranceClient {

    /**
     * 抓拍记录
     *
     * @return
     */
    @Get("/api/v2.0/entrance/device/getDevicePhotographList")
    Result<PageResVo<DevicePhotographPageResponseVo>> getDevicePhotographList(@Header("token") String token,
                                                                              @Query DevicePhotographPageRequestVo devicePhotographPageRequestVo);

    /**
     * 手动抓拍
     *
     * @param token
     * @param deviceAddr 设备地址
     * @return
     */
    @Get("/api/v2.0/entrance/device/photograph")
    Result<?> photograph(@Header("token") String token,
                         @Query("deviceAddr") String deviceAddr);
}
