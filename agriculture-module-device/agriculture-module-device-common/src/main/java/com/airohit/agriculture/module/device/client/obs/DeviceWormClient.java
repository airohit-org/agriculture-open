package com.airohit.agriculture.module.device.client.obs;

import com.airohit.agriculture.module.device.vo.farmyun.PageResVo;
import com.airohit.agriculture.module.device.vo.farmyun.Result;
import com.airohit.agriculture.module.device.vo.farmyun.worm.*;
import com.dtflys.forest.annotation.*;

import java.util.List;

/**
 * Created with IDEA
 * 虫情设备
 *
 * @author :shiminghao
 * @date :2023/5/6 13:16
 */
@BaseRequest(baseURL = "http://api.farm.0531yun.cn")
public interface DeviceWormClient {

    /**
     * 批量获取设备详情
     *
     * @param token
     * @param deviceAddr 设备地址（多个用英文逗号分隔，最多同时获取5个设备信息）
     * @return
     */
    @Get("/api/v2.0/worm/device/getBtchDeviceDO")
    Result<List<BtchDeviceResponseVo>> getBtchDeviceDO(@Header("token") String token,
                                                       @Query("deviceAddr") String deviceAddr);

    /**
     * 修改设备
     *
     * @return
     */
    @Post("/api/v2.0/worm/device/updateDevice")
    Result<?> updateDevice(@Header("token") String token, @JSONBody UpdateDeviceRequestVo updateDeviceRequestVo);

    /**
     * 获取设备自动模式时间
     *
     * @param token
     * @param deviceAddr 设备地址
     * @return
     */
    @Get("/api/v2.0/worm/device/getWormAutoMode")
    Result<WormAutoModeResponseVO> getWormAutoMode(@Header("token") String token,
                                                   @Query("deviceAddr") String deviceAddr);

    /**
     * 修改设备自动模式时间
     *
     * @return
     */
    @Post("/api/v2.0/worm/device/modWormAutoMode")
    Result<?> modWormAutoMode(@Header("token") String token, @JSONBody ModWormAutoModeRequestVO modWormAutoModeRequestVO);

    /**
     * 手自动模式切换
     *
     * @return
     */
    @Post("/api/v2.0/worm/device/deviceMode")
    Result<?> deviceMode(@Header("token") String token, @JSONBody DeviceModeRequestVo deviceModeRequestVo);


    /**
     * 虫情设备下发命令
     *
     * @return
     */
    @Post("/api/v2.0/worm/device/deviceOper")
    Result<?> deviceOper(@Header("token") String token, @JSONBody DeviceOperRequestVo deviceOperRequestVo);

    /**
     * 虫情设备历史记录
     *
     * @return
     */
    @Get("/api/v2.0/worm/deviceData/getWormHistoryData")
    Result<PageResVo<WormHistoryDataPageResponseVo>> getWormHistoryData(@Header("token") String token,
                                                                        @Query WormHistoryDataPageRequestVo wormHistoryDataPageRequestVo);

    /**
     * 虫情区域统计
     *
     * @return
     */
    @Get("/api/v2.0/worm/deviceData/getWormStatisticsByGroup")
    Result<List<WormStatisticsByGroupResponseVo>> getWormStatisticsByGroup(@Header("token") String token,
                                                                           @Query WormStatisticsByGroupRequestVo wormStatisticsByGroupRequestVo);


    /**
     * 虫情设备分析报表
     *
     * @return
     */
    @Get("/api/v2.0/worm/deviceData/getWormDataList")
    Result<PageResVo<WormDataResponseVo>> getWormDataList(@Header("token") String token,
                                                          @Query WormDataRequestVo wormDataRequestVo);

    /**
     * 虫情设备分析报表记录（最新一条记录）
     *
     * @return
     */
    @Get("/api/v2.0/worm/deviceData/getWormDataAndWormDataAIBy")
    Result<WormDataResponseVo> getWormDataAndWormDataAIBy(@Header("token") String token,
                                                          @Query("deviceAddr") String deviceAddr);

    /**
     * 害虫自动识别
     *
     * @return
     */
    @Get("/api/v2.0/worm/deviceData/analysistWorm")
    Result<List<AnalysistWormResponseVo>> analysistWorm(@Header("token") String token,
                                                        @Query("recordId") String recordId);
}
