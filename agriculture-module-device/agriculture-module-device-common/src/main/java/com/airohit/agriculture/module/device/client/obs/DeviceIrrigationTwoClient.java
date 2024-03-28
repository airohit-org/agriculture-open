package com.airohit.agriculture.module.device.client.obs;

import com.airohit.agriculture.module.device.vo.farmyun.PageResVo;
import com.airohit.agriculture.module.device.vo.farmyun.Result;
import com.airohit.agriculture.module.device.vo.farmyun.irrigation.two.*;
import com.dtflys.forest.annotation.*;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/6 14:40
 */
@BaseRequest(baseURL = "http://api.farm.0531yun.cn")
public interface DeviceIrrigationTwoClient {

    /**
     * 批量获取设备详情
     *
     * @param token
     * @param devAddr 设备地址（多个用英文逗号分隔，最多同时获取5个设备信息）
     * @return
     */
    @Get("/api/v2.0/irrigation/device/getDeviceIii")
    Result<List<DeviceIiiResponseVo>> getDeviceIii(@Header("token") String token,
                                                   @Query("devAddr") String devAddr);

    /**
     * 修改设备
     *
     * @return
     */
    @Post("/api/v2.0/irrigation/device/updateDevInfo")
    Result<?> updateDevInfo(@Header("token") String token, @JSONBody UpdateDevInfoRequestVo updateDevInfoRequestVo);

    /**
     * 获取节点列表
     *
     * @param token
     * @param devAddr 设备地址
     * @return
     */
    @Get("/api/v2.0/irrigation/node/getDeviceNodeList")
    Result<List<DeviceNodeListResponseVo>> getDeviceNodeList(@Header("token") String token,
                                                             @Query("devAddr") String devAddr);

    /**
     * 修改节点信息
     *
     * @return
     */
    @Post("/api/v2.0/irrigation/node/updateDeviceNode")
    Result<?> updateDeviceNode(@Header("token") String token,
                               @JSONBody UpdateDeviceNodeResponseVo updateDeviceNodeResponseVo);

    /**
     * 批量开关节点（使能）
     *
     * @return
     */
    @Post("/api/v2.0/irrigation/node/batchNodeEnable")
    Result<?> batchNodeEnable(@Header("token") String token,
                              @JSONBody BatchNodeEnableRequestVo batchNodeEnableRequestVo);

    /**
     * 获取节点遥调信息
     *
     * @param token
     * @param factorId 节点id
     * @return
     */
    @Get("/api/v2.0/irrigation/factor/getIrrigationFactorRegulating")
    Result<List<IrrigationFactorRegulatingResponseVo>> getIrrigationFactorRegulating(@Header("token") String token,
                                                                                     @Query("factorId") String factorId);

    /**
     * 更新节点遥调信息（此接口为删除原有信息重新添加）
     *
     * @return
     */
    @Post("/api/v2.0/irrigation/factor/replaceTbIrrigationFactorRegulating")
    Result<?> replaceTbIrrigationFactorRegulating(@Header("token") String token,
                                                  @JSONBody ReplaceTbIrrigationFactorRegulatingRequestVo replaceTbIrrigationFactorRegulatingRequestVo);

    /**
     * 历史记录
     *
     * @return
     */
    @Get("/api/v2.0/irrigation/node/getHistoryDataList")
    Result<PageResVo<HistoryDataListPageResponseVo>> getHistoryDataList(@Header("token") String token,
                                                                        @Query HistoryDataListPageRequestVo historyDataListPageRequestVo);

    /**
     * 修改阀门工作模式
     *
     * @return
     */
    @Post("/api/v2.0/irrigation/factor/updateFactorMode")
    Result<?> updateFactorMode(@Header("token") String token, @JSONBody FactorModeRequestVo factorModeRequestVo);

    /**
     * 手动开启关闭阀门
     *
     * @return
     */
    @Post("/api/v2.0/irrigation/node/manualControlValve")
    Result<?> manualControlValve(@Header("token") String token, @JSONBody ManualControlValveRequestVo manualControlValveRequestVo);
}
