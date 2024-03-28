package com.airohit.agriculture.module.device.client.obs;

import com.airohit.agriculture.module.device.vo.farmyun.PageResVo;
import com.airohit.agriculture.module.device.vo.farmyun.Result;
import com.airohit.agriculture.module.device.vo.farmyun.met.*;
import com.dtflys.forest.annotation.*;

import java.util.List;

/**
 * Created with IDEA
 * 气象设备
 *
 * @author :shiminghao
 * @date :2023/5/5 14:55
 */
@BaseRequest(baseURL = "http://api.farm.0531yun.cn")
public interface DeviceMetClient {

    /**
     * 获取设备全部信息
     *
     * @param token
     * @param deviceStr 设备地址（多个用英文逗号分隔，最多同时获取5个设备信息）
     * @return
     */
    @Get("/api/v2.0/met/device/getDeviceAllInfo")
    Result<List<DeviceAllInfoResponseVo>> getDeviceAllInfo(@Header("token") String token,
                                                           @Query("deviceStr") String deviceStr);

    /**
     * 根据设备地址获取节点信息
     *
     * @param token
     * @param deviceStr 设备地址
     * @return
     */
    @Get("/api/v2.0/met/device/listTargetNodeInfo")
    Result<List<TargetNodeInfoResponseVo>> listTargetNodeInfo(@Header("token") String token,
                                                              @Query("deviceStr") String deviceStr);

    /**
     * 根据设备地址获取已启用的节点信息
     *
     * @param token
     * @param deviceStr 设备地址
     * @return
     */
    @Get("/api/v2.0/met/device/listTargetEnabledNode")
    Result<List<TargetNodeEnabledInfoResponseVo>> listTargetEnabledNode(@Header("token") String token,
                                                                        @Query("deviceStr") String deviceStr);

    /**
     * 根据节点编号获取遥调信息
     *
     * @param token
     * @param deviceStr 设备地址
     * @param nodeId    节点编号
     * @return
     */
    @Get("/api/v2.0/met/device/listTargetRegulating")
    Result<List<TargetNodeEnabledInfoResponseVo>> listTargetRegulating(@Header("token") String token,
                                                                       @Query("deviceStr") String deviceStr,
                                                                       @Query("nodeId") String nodeId);

    /**
     * 修改指定设备全部节点的可用状态
     *
     * @return
     */
    @Post("/api/v2.0/met/device/updateAllOfNodesEnable")
    Result<?> updateAllOfNodesEnable(@Header("token") String token, @JSONBody UpdateAllOfNodesEnableRequestVo UpdateAllOfNodesEnableRequestVo);

    /**
     * 更新设备信息
     *
     * @return
     */
    @Post("/api/v2.0/met/device/updateDevice")
    Result<?> updateDevice(@Header("token") String token, @JSONBody UpdateDeviceRequestVo UpdateDeviceRequestVo);

    /**
     * 更新节点信息
     *
     * @return
     */
    @Post("/api/v2.0/met/device/updateNodeInfo")
    Result<?> updateNodeInfo(@Header("token") String token, @JSONBody UpdateNodeInfoRequestVo updateNodeInfoRequestVo);

    /**
     * 更新遥调信息（此接口为删除原有信息重新添加）
     *
     * @return
     */
    @Post("/api/v2.0/met/device/updateRegulatingInfo")
    Result<?> updateRegulatingInfo(@Header("token") String token, @JSONBody UpdateRegulatingInfoRequestVo updateRegulatingInfoRequestVo);

    /**
     * 根据条件获取历史数据
     *
     * @return
     */
    @Get("/api/v2.0/met/history/getHistoryDataList")
    Result<PageResVo<HistoryDataPageResponseVo>> getHistoryDataList(@Header("token") String token,
                                                                    @Query HistoryDataPageRequestVo historyDataPageRequestVo);
}
