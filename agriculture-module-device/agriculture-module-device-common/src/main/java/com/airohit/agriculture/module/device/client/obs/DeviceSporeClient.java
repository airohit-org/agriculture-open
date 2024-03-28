package com.airohit.agriculture.module.device.client.obs;

import com.airohit.agriculture.module.device.vo.farmyun.PageResVo;
import com.airohit.agriculture.module.device.vo.farmyun.Result;
import com.airohit.agriculture.module.device.vo.farmyun.spore.*;
import com.dtflys.forest.annotation.*;

import java.util.List;

/**
 * Created with IDEA
 * 孢子设备
 *
 * @author :shiminghao
 * @date :2023/5/5 16:23
 */
@BaseRequest(baseURL = "http://api.farm.0531yun.cn")
public interface DeviceSporeClient {

    /**
     * 批量获取设备详情
     *
     * @param token
     * @param deviceAddr 设备地址（多个用英文逗号分隔，最多同时获取5个设备信息）
     * @return
     */
    @Get("/api/v2.0/spore/device/getBtchDeviceDO")
    Result<List<BtchDeviceResponseVo>> getBtchDeviceDO(@Header("token") String token,
                                                       @Query("deviceAddr") String deviceAddr);

    /**
     * 修改设备
     *
     * @return
     */
    @Post("/api/v2.0/spore/device/updateDevice")
    Result<?> updateDevice(@Header("token") String token, @JSONBody UpdateDeviceRequestVo updateDeviceRequestVo);


    /**
     * 获取设备自动模式时间
     *
     * @param token
     * @param deviceAddr 设备地址
     * @return
     */
    @Get("/api/v2.0/spore/device/getSporeAutoMode")
    Result<SporeAutoModeResponseVO> getSporeAutoMode(@Header("token") String token,
                                                     @Query("deviceAddr") String deviceAddr);

    /**
     * 修改设备自动模式时间
     *
     * @return
     */
    @Post("/api/v2.0/spore/device/modSporeAutoMode")
    Result<?> modSporeAutoMode(@Header("token") String token, @JSONBody ModSporeAutoModeRequestVo modSporeAutoModeRequestVo);

    /**
     * 手自动模式切换
     *
     * @return
     */
    @Post("/api/v2.0/spore/device/deviceMode")
    Result<?> deviceMode(@Header("token") String token, @JSONBody DeviceModeRequestVo deviceModeRequestVo);

    /**
     * 孢子设备下发命令
     *
     * @return
     */
    @Post("/api/v2.0/spore/device/deviceOper")
    Result<?> deviceOper(@Header("token") String token, @JSONBody DeviceOperRequestVo deviceOperRequestVo);

    /**
     * 孢子设备历史记录
     *
     * @return
     */
    @Get("/api/v2.0/spore/deviceData/getSporeHistoryDataDO")
    Result<PageResVo<SporeHistoryDataPageResponseVo>> getSporeHistoryDataDO(@Header("token") String token,
                                                                            @Query SporeHistoryDataPageRequestVo sporeHistoryDataPageRequestVo);

    /**
     * 孢子设备分析报表记录
     *
     * @return
     */
    @Get("/api/v2.0/spore/deviceData/getSporeDataAndSporeDataAIByDO")
    Result<PageResVo<SporeDataAndSporeDataAIByPageResponseVo>> getSporeDataAndSporeDataAIByDO(@Header("token") String token,
                                                                                              @Query SporeDataAndSporeDataAIByPageRequestVo sporeDataAndSporeDataAIByPageRequestVo);
}
