package com.airohit.agriculture.module.device.client.obs;

import com.airohit.agriculture.module.device.vo.farmyun.PageResVo;
import com.airohit.agriculture.module.device.vo.farmyun.Result;
import com.airohit.agriculture.module.device.vo.farmyun.soil.*;
import com.dtflys.forest.annotation.*;

import java.util.List;

/**
 * Created with IDEA
 * 墒情设备
 *
 * @author :shiminghao
 * @date :2023/5/5 16:03
 */
@BaseRequest(baseURL = "http://api.farm.0531yun.cn")
public interface DeviceSoilClient {
    /**
     * 获取设备全部信息
     *
     * @param token
     * @param deviceStr 设备地址（多个用英文逗号分隔，最多同时获取5个设备信息）
     * @return
     */
    @Get("/api/v2.0/soil/device/getDeviceAllInfo")
    Result<List<DeviceAllInfoResponseVo>> getDeviceAllInfo(@Header("token") String token,
                                                           @Query("deviceStr") String deviceStr);

    /**
     * 更新设备信息
     *
     * @return
     */
    @Post("/api/v2.0/soil/device/updateDevice")
    Result<?> updateDevice(@Header("token") String token, @JSONBody UpdateDeviceRequestVo UpdateDeviceRequestVo);

    /**
     * 更新节点信息
     *
     * @return
     */
    @Post("/api/v2.0/soil/device/updateNodeInfo")
    Result<?> updateNodeInfo(@Header("token") String token, @JSONBody UpdateNodeInfoRequestVo updateNodeInfoRequestVo);

    /**
     * 根据条件获取历史数据
     *
     * @return
     */
    @Get("/api/v2.0/soil/history/getHistoryDataList")
    Result<PageResVo<HistoryDataPageResponseVo>> getHistoryDataList(@Header("token") String token,
                                                                    @Query HistoryDataPageRequestVo historyDataPageRequestVo);
}
